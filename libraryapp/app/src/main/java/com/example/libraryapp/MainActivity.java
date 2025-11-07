package com.example.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbHelper db;
    ArrayList<Book> books;
    BookAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbHelper(this);
        listView = findViewById(R.id.listBooks);

        loadBooks();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(this, EditBookActivity.class));
        });

        listView.setOnItemClickListener((p, v, pos, id) -> {
            Book b = books.get(pos);
            Intent i = new Intent(this, EditBookActivity.class);
            i.putExtra("id", b.id);
            startActivity(i);
        });

        listView.setOnItemLongClickListener((p, v, pos, id) -> {
            db.deleteBook(books.get(pos).id);
            loadBooks();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }

    private void loadBooks() {
        books = new ArrayList<>();
        Cursor c = db.getAllBooks();

        while (c.moveToNext()) {

            long id = c.getLong(c.getColumnIndexOrThrow(DbHelper.COLUMN_ID));
            String title = c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_TITLE));
            String author = c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_AUTHOR));
            String genre = c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_GENRE));
            Integer year = c.isNull(c.getColumnIndexOrThrow(DbHelper.COLUMN_YEAR))
                    ? null
                    : c.getInt(c.getColumnIndexOrThrow(DbHelper.COLUMN_YEAR));

            books.add(new Book(id, title, author, genre, year));
        }
        c.close();

        adapter = new BookAdapter(this, books);
        listView.setAdapter(adapter);
    }
}
