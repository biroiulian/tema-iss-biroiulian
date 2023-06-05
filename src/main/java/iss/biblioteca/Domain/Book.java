package iss.biblioteca.Domain;

public class Book {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private bookState bookState;

    public Book(int id, String title, String author, String publisher, int year, bookState bookState) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.bookState = bookState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public iss.biblioteca.Domain.bookState getBookState() {
        return bookState;
    }

    public void setBookState(iss.biblioteca.Domain.bookState bookState) {
        this.bookState = bookState;
    }

    @Override
    public String toString() {
        return "Book: " + title + ", written by " + author;
    }
}
