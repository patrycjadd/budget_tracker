package com.example.budgettracker.repository;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.budgettracker.model.CategoryEntity;

import java.util.concurrent.Executors;

@Database(entities = {CategoryEntity.class}, version = 4)
public abstract class CategoryDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();

    private static volatile CategoryDatabase INSTANCE;

    public static CategoryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CategoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CategoryDatabase.class, "category_database.db").fallbackToDestructiveMigration().addCallback(new RoomDatabase.Callback() {
                        public void onCreate(SupportSQLiteDatabase db) {
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    new CategoryRepository((Application) context).insertCategory(new CategoryEntity(1, "Name"));
                                }
                            });
                        } }).build();
                }
            }
        }
        return INSTANCE;
    }
}
