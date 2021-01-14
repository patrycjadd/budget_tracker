package com.example.budgettracker.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgettracker.R;
import com.example.budgettracker.model.converter.TransactionEntityToTransactionDto;
import com.example.budgettracker.viewmodel.TransactionViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class HistoryActivity extends AppCompatActivity {

    private TransactionViewModel transactionViewModel;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private TransactionEntityToTransactionDto converter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        customAdapter = new CustomAdapter(getApplicationContext(), new ArrayList<>());
        converter = new TransactionEntityToTransactionDto(getApplication());
        recyclerView.setAdapter(customAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        Button buttonDelete = findViewById(R.id.deleteButton);
        buttonDelete.setOnClickListener(v -> {
            transactionViewModel.deleteTransactions();
            customAdapter.clear();
        });
        try {
            transactionViewModel.getAllTransactions().observe(this, transactions -> {
                try {
                    Collections.reverse(transactions);
                    customAdapter.setData(converter.convertToDtos(transactions));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
