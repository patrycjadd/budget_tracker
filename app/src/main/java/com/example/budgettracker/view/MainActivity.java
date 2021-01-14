package com.example.budgettracker.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.example.budgettracker.R;
import com.example.budgettracker.chart.BudgetBarChart;
import com.example.budgettracker.model.CategoryEntity;
import com.example.budgettracker.repository.TransactionRepository;
import com.example.budgettracker.viewmodel.TransactionViewModel;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final int RC_OCR_CAPTURE = 9003;

    private Button scanButton, expenseButton, incomeButton, historyButton;
    private TransactionViewModel transactionViewModel;
    private List<CategoryEntity> categoriesList;
    private Dialog dialog;
    private TransactionRepository transactionRepository;
    private List<DataEntry> data;
    private TextView budget;
    private AnyChartView anyChartView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViews();
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        transactionRepository = new TransactionRepository(this.getApplication());
        data = new ArrayList<>();
        dialog = new Dialog(MainActivity.this, transactionViewModel);
        expenseButton.setOnClickListener(v -> dialog.showDialog(categoriesList, null, false));
        incomeButton.setOnClickListener(v -> dialog.showDialog(categoriesList, null, true));
        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(intent);
        });
        scanButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, true);

            startActivityForResult(intent, RC_OCR_CAPTURE);

        });
        BudgetBarChart budgetBarChart = new BudgetBarChart(anyChartView);

        try {
            transactionViewModel.getAllTransactions().observe(this, transactions -> {
                try {
                    DecimalFormat df = new DecimalFormat("#.00");
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    if (transactions.size() == 0) {
                            budget.setText("00.00  PLN");
                        return;
                    }
                        budget.setText(df.format(transactionRepository.getSumOfIncomes()+transactionRepository.getSumOfExpenses()) + "  PLN");
                } catch (NullPointerException e) {
                        budget.setText("00.00");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        transactionViewModel.getIncomesAndExpenseListGroupedByMonth().observe(this, transactions -> {
            data.removeAll(data);
            try {
                DecimalFormat df = new DecimalFormat("#.00");
                df.setRoundingMode(RoundingMode.HALF_UP);
                for (int i = 0; i < transactions.size(); i++) {
                    data.add(new CustomDataEntry(dateToDayNMonth(transactions.get(i).getDateOfTransaction()), transactions.get(i).getIncomeValue(), transactions.get(i).getExpenseValue()));
                }
                budgetBarChart.setChartData(data);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

        transactionViewModel.getAllCategories().observe(this, categories -> {
            categoriesList = new ArrayList<>();
            categoriesList.addAll(categories);
        });
    }

    private void setViews(){
        setContentView(R.layout.activity_main);
        budget = findViewById(R.id.budget);
        expenseButton = findViewById(R.id.expense_button);
        incomeButton = findViewById(R.id.income_button);
        historyButton = findViewById(R.id.history_button);
        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        anyChartView.setBackgroundColor(Color.CYAN);
        scanButton = findViewById(R.id.scan_button);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    String replace = text.replace("PLN", "").replace(" ", "");
                    String newRep = replace.replace(",", ".");
                    try{dialog.showDialog(categoriesList, Double.parseDouble(newRep), false);}
                    catch (NumberFormatException e){
                        Toast.makeText(this.getApplicationContext(), "You can only scan numbers. Try again.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this.getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(this.getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }

    private String dateToDayNMonth(String stringDate){
        Date date = null;
        try {
            date  = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        long dateInMillis = date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        return sdf.format(new Date(dateInMillis));
    }
}