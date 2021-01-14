package com.example.budgettracker.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.budgettracker.model.converter.DateConverter;

@Entity(tableName = "Transaction")
@TypeConverters(DateConverter.class)
public class TransactionEntity {

    @PrimaryKey(autoGenerate = true)
    private long transactionId;
    private double incomeValue;
    private double expenseValue;
    private String dateOfTransaction;
    private long categoryId;

    public TransactionEntity(double incomeValue, double expenseValue, String dateOfTransaction, long categoryId) {
        this.incomeValue = incomeValue;
        this.expenseValue = expenseValue;
        this.dateOfTransaction = dateOfTransaction;
        this.categoryId = categoryId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public double getIncomeValue() {
        return incomeValue;
    }

    public void setIncomeValue(double incomeValue) {
        this.incomeValue = incomeValue;
    }

    public double getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(double expenseValue) {
        this.expenseValue = expenseValue;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(String dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
