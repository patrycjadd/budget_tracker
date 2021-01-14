package com.example.budgettracker.model;

public class TransactionDto {

    private long transactionId;
    private double incomeValue;
    private double expenseValue;
    private String dateOfTransaction;
    private String categoryName;

    public TransactionDto() {
    }

    public TransactionDto(long transactionId, double incomeValue, double expenseValue, String dateOfTransaction, String categoryName) {
        this.transactionId = transactionId;
        this.incomeValue = incomeValue;
        this.expenseValue = expenseValue;
        this.dateOfTransaction = dateOfTransaction;
        this.categoryName = categoryName;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
