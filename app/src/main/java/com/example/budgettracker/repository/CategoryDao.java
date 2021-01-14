package com.example.budgettracker.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.budgettracker.model.CategoryEntity;
import java.util.List;

@Dao
public interface CategoryDao {

    @Transaction
    @Insert
    void insertCategory(CategoryEntity category);

    @Transaction
    @Query("Select * from `Category`")
    LiveData<List<CategoryEntity>> getAllCategories();

    @Transaction
    @Query("select name from    Category where categoryId = :categoryId")
    String getCategoryName(long categoryId);
}
