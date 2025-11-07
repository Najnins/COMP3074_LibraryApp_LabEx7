package com.example.libraryapp;

public class Book {

    public long id;
    public String title;
    public String author;
    public String genre;
    public Integer year;

    public Book(long id, String title, String author, String genre, Integer year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
    }
}
