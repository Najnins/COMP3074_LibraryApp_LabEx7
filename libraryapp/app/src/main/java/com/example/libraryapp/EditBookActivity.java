package com.example.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditBookActivity extends AppCompatActivity {

    EditText etTitle, etAuthor, etGenre, etYear;
    Button btnSave;
    DbHelper db;
    long editId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        db = new DbHelper(this);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        btnSave = findViewById(R.id.btnSave);

        if (getIntent().hasExtra("id")) {
            editId = getIntent().getLongExtra("id", -1);
            loadBook(editId);
        }

        btnSave.setOnClickListener(v -> saveBook());
    }

    private void loadBook(long id) {
        Cursor c = db.getBook(id);

        if (c.moveToFirst()) {

            etTitle.setText(c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_TITLE)));
            etAuthor.setText(c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_AUTHOR)));
            etGenre.setText(c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_GENRE)));

            if (!c.isNull(c.getColumnIndexOrThrow(DbHelper.COLUMN_YEAR))) {
                etYear.setText(String.valueOf(
                        c.getInt(c.getColumnIndexOrThrow(DbHelper.COLUMN_YEAR))
                ));
            }
        }
        c.close();
    }

    private void saveBook() {

        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String genre = etGenre.getText().toString().trim();
        String y = etYear.getText().toString().trim();

        Integer year = y.isEmpty() ? null : Integer.parseInt(y);

        if (editId == -1) {
            db.addBook(title, author, genre, year);
        } else {
            db.updateBook(editId, title, author, genre, year);
        }

        finish();
    }
}
