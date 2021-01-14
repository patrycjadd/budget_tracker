package com.example.budgettracker.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.budgettracker.model.TransactionEntity;

@Database(entities = {TransactionEntity.class}, version = 3)
public abstract class TransactionDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();

    private static volatile TransactionDatabase INSTANCE;

    public static TransactionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TransactionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TransactionDatabase.class, "transaction_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
