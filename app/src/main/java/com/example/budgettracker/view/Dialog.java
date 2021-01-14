package com.example.budgettracker.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgettracker.R;
import com.example.budgettracker.model.CategoryEntity;
import com.example.budgettracker.model.TransactionEntity;
import com.example.budgettracker.util.TimeHelper;
import com.example.budgettracker.viewmodel.TransactionViewModel;

import java.util.ArrayList;
import java.util.List;

public class Dialog {

    private final Context context;
    private final TransactionViewModel transactionViewModel;
    private Spinner spinner;
    private EditText userInput;
    private long categoryId;
    private android.app.Dialog dialog;
    private boolean isNewCategoryDialog;

    public Dialog(Context context, TransactionViewModel transactionViewModel) {
        this.context = context;
        this.transactionViewModel = transactionViewModel;
        this.isNewCategoryDialog = false;
    }

    public void showDialog(List<CategoryEntity> categories, Double amount, boolean isIncome) {
        dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        userInput = dialog.findViewById(R.id.value);
        Button okButton = dialog.findViewById(R.id.confirmButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        List<CategoryEntity> spiennerArray = new ArrayList<>();
        spinner = dialog.findViewById(R.id.category);

        ArrayAdapter<CategoryEntity> adapter = new ArrayAdapter<CategoryEntity>(context, android.R.layout.simple_spinner_item , spiennerArray){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
                TextView label = (TextView) super.getView(position, convertView, parent);
                label.setTextColor(Color.BLACK);
                // Then you can get the current item using the values array (Users array) and the current position
                // You can NOW reference each method you has created in your bean object (User class)
                CategoryEntity item = getItem(position);
                label.setText(item.getName());

                // And finally return your dynamic (or custom) view for each spinner item
                return label;
            }

            // And here is when the "chooser" is popped up
            // Normally is the same view, but you can customize it if you want
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                TextView label = (TextView) super.getDropDownView(position, convertView, parent);
                label.setTextColor(Color.BLACK);
                CategoryEntity item = getItem(position);
                label.setText(item.getName());

                return label;
            }
        };
        if(amount!= null){
            userInput.setText(amount.toString());
        }
        adapter.add(new CategoryEntity("Choose category"));
        adapter.add(new CategoryEntity("Create new category"));
        adapter.addAll(categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(categorySelectedListener);
        okButton.setOnClickListener(v -> onSubmitted( categories, dialog, userInput.getText().toString(), isIncome));
        cancelButton.setOnClickListener(view -> onDismissed(dialog));
        dialog.show();
    }

    private double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }
    private void onSubmitted(List<CategoryEntity> categoryEntities, final DialogInterface dialog, String value, final boolean isIncome) {
        if (isNewCategoryDialog){
            boolean categoryAlreadyExists = categoryEntities.stream().anyMatch(categoryEntity -> categoryEntity.getName().equalsIgnoreCase(value));
            if(categoryAlreadyExists){
                Toast.makeText(context, "This category already exists", Toast.LENGTH_LONG).show();
            } else {
                transactionViewModel.insertCategory(new CategoryEntity(value));
            }
            isNewCategoryDialog = false;
        } else {
            double amount = ParseDouble(value);
            if (amount == 0) {
                Toast.makeText(context.getApplicationContext(), "Insert value", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isIncome) {
                amount = -amount;
                transactionViewModel.insert(new TransactionEntity(0, amount, TimeHelper.getActualDate(), categoryId));

            } else {
                transactionViewModel.insert(new TransactionEntity(amount, 0, TimeHelper.getActualDate(), categoryId));

            }
        }

        dialog.dismiss();

    }
    private void onDismissed(final DialogInterface dialog) {
        dialog.dismiss();
    }
    AdapterView.OnItemSelectedListener categorySelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
            if(position == 1){
                isNewCategoryDialog =true;
                userInput.setHint("Enter category");
                userInput.setInputType(InputType.TYPE_CLASS_TEXT);
                dialog.show();
            }
            Log.i("categoryId", String.valueOf(position));
            categoryId = ((CategoryEntity) spinner.getItemAtPosition(position)).getCategoryId();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    };
}
