package com.example.budgettracker.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.budgettracker.model.TransactionEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TransactionRepository {
    private TransactionDao transactionDao;

    public TransactionRepository(Application application) {
        TransactionDatabase db = TransactionDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
    }

    public Double getSumOfIncomes() throws ExecutionException, InterruptedException {
        return new getSumOfIncomes(transactionDao).execute().get();
    }

    public Double getSumOfExpenses() throws ExecutionException, InterruptedException {
        return new getSumOfExpenses(transactionDao).execute().get();
    }

    public List<TransactionEntity> getAllTransactionASC() throws ExecutionException, InterruptedException {
        return new getAllTransactionAsc(transactionDao).execute().get();
    }

    public void insertTransaction(TransactionEntity transaction) {
        new insertAsyncTask(transactionDao).execute(transaction);
    }

    public void deleteTransactions() {
        new deleteAsyncTask(transactionDao).execute();
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() throws ExecutionException, InterruptedException {
        return transactionDao.getAllTransactions();
    }


//    public LiveData<List<TransactionEntity>> getAllIncomesGroupedByMonth() throws ExecutionException, InterruptedException {
//        return transactionDao.getAllIncomesGroupedByMonth();
//    }
//
//    public LiveData<List<TransactionEntity>> getAllExpensesGroupedByMonth() throws ExecutionException, InterruptedException {
//        return transactionDao.getAllExpensesGroupedByMonth();
//    }

    public LiveData<List<TransactionEntity>> getAllIncomesExpensesGroupedByMonth() throws ExecutionException, InterruptedException {
        return transactionDao.getAllIncomesExpensesGroupedByMonth();
    }

    private static class insertAsyncTask extends AsyncTask<TransactionEntity, Void, Void> {

        private TransactionDao mAsyncTaskDao;

        insertAsyncTask(TransactionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TransactionEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private TransactionDao mAsyncTaskDao;

        deleteAsyncTask(TransactionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class getSumOfIncomes extends AsyncTask<Void, Void, Double> {

        private TransactionDao mAsyncTaskDao;

        getSumOfIncomes(TransactionDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Double doInBackground(Void... voids) {
            return mAsyncTaskDao.getSumOfIncomes();
        }
    }

    private static class getSumOfExpenses extends AsyncTask<Void, Void, Double> {

        private TransactionDao mAsyncTaskDao;

        getSumOfExpenses(TransactionDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Double doInBackground(Void... voids) {
            return mAsyncTaskDao.getSumOfExpenses();
        }
    }

    private static class getAllTransactionAsc extends AsyncTask<Void, Void, List<TransactionEntity>> {

        private TransactionDao mAsyncTaskDao;

        getAllTransactionAsc(TransactionDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected List<TransactionEntity> doInBackground(Void... voids) {
            return mAsyncTaskDao.getAllTransactionASC();
        }
    }
}
