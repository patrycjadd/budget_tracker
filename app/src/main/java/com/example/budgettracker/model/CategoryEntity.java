package com.example.budgettracker.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    private long categoryId;
    private String name;

    public CategoryEntity(String name) {
        this.name = name;
    }

    @Ignore
    public CategoryEntity(long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
