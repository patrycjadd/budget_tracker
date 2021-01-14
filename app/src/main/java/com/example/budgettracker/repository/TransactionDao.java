package com.example.budgettracker.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgettracker.model.TransactionEntity;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insert(TransactionEntity transaction);

    @Query("delete  from `Transaction` ")
    void deleteAll();

    @Query("select sum(incomeValue) from `Transaction`")
    Double getSumOfIncomes();

    @Query("select sum(expenseValue) from `Transaction`")
    Double getSumOfExpenses();

//    @Query("select transactionId, sum(incomeValue) as value, isIncome,categoryId, dateOfTransaction from `Transaction` where isIncome=1 group by strftime('%Y-%m', dateOfTransaction)  order by dateOfTransaction")
//    LiveData<List<TransactionEntity>> getAllIncomesGroupedByMonth();
//
//    @Query("select transactionId, sum(incomeValue) as value, isIncome,categoryId, dateOfTransaction from `Transaction` where isIncome=0 group by strftime('%Y-%m', dateOfTransaction)  order by dateOfTransaction")
//    LiveData<List<TransactionEntity>> getAllExpensesGroupedByMonth();

    @Query("select transactionId, sum(incomeValue) as incomeValue, sum(expenseValue) as expenseValue, categoryId, dateOfTransaction from `Transaction`group by strftime('%Y-%m', dateOfTransaction)  order by dateOfTransaction")
    LiveData<List<TransactionEntity>> getAllIncomesExpensesGroupedByMonth();

    @Query("select transactionId,incomeValue, expenseValue, categoryId,dateOfTransaction from `Transaction` order by dateOfTransaction desc")
    LiveData<List<TransactionEntity>> getAllTransactions();

    @Query("select transactionId, incomeValue, expenseValue, categoryId, dateOfTransaction from `Transaction` order by dateOfTransaction asc")
    List<TransactionEntity> getAllTransactionASC();
}
