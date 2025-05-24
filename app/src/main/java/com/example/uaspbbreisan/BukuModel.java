package com.example.uaspbbreisan;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BukuModel {
    private int book_id;
    private String title;
    private String author;
    private int stock;

    public BukuModel(int book_id, String title, String author, int stock) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.stock = stock;
    }

    public int getBookId() { return book_id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getStock() { return stock; }
}