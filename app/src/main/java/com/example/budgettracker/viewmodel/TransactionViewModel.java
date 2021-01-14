package com.example.budgettracker.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budgettracker.model.CategoryEntity;
import com.example.budgettracker.model.TransactionEntity;
import com.example.budgettracker.repository.CategoryRepository;
import com.example.budgettracker.repository.TransactionRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository transactionRepository;
    private CategoryRepository categoryRepository;
    private Double sumOfIncomes;
    private Double sumOfExpenses;
    private LiveData<List<TransactionEntity>> transactionList;
    private LiveData<List<TransactionEntity>> incomesAndExpenseListGroupedByMonth;
    private LiveData<List<CategoryEntity>> categoriesList;

    public TransactionViewModel(Application application) throws ExecutionException, InterruptedException {
        super(application);

        transactionRepository = new TransactionRepository(application);
        categoryRepository = new CategoryRepository(application);
        sumOfIncomes = transactionRepository.getSumOfIncomes();
        sumOfExpenses = transactionRepository.getSumOfExpenses();
        transactionList = transactionRepository.getAllTransactions();
        incomesAndExpenseListGroupedByMonth = transactionRepository.getAllIncomesExpensesGroupedByMonth();
        categoriesList = categoryRepository.getAllCategories();
    }

    public void setUpViewModel() throws ExecutionException, InterruptedException {

    }

    public Double getSumOfIncomes() {
        return sumOfIncomes;
    }

    public void insert(TransactionEntity transaction) {
        transactionRepository.insertTransaction(transaction);
    }

    public void insertCategory(CategoryEntity categoryEntity) {
        categoryRepository.insertCategory(categoryEntity);
    }


    public LiveData<List<TransactionEntity>> getAllTransactions() throws ExecutionException, InterruptedException {
        if (transactionList == null) {
            transactionList = transactionRepository.getAllTransactions();
        }
        return transactionList;
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        if(categoriesList == null) {
            categoriesList = categoryRepository.getAllCategories();
        }
        return categoriesList;
    }

    public LiveData<List<TransactionEntity>> getIncomesAndExpenseListGroupedByMonth() {
        return incomesAndExpenseListGroupedByMonth;
    }

    public void deleteTransactions() {
        transactionRepository.deleteTransactions();
    }

}

