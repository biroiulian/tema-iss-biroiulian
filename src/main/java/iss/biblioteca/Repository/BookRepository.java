package iss.biblioteca.Repository;

import iss.biblioteca.Domain.Book;
import iss.biblioteca.Domain.bookState;

import java.sql.*;
import java.text.ParseException;
import java.util.Objects;
import java.util.Vector;

import static iss.biblioteca.Domain.bookState.*;

public class BookRepository {
    private String url;
    private Connection connection;
    private String selectStatement;

    public BookRepository(String url){
        this.url = url;
        this.selectStatement = "SELECT * FROM books";
        this.connection = this.connect();
    }

    public Connection connect() {
        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(this.url);

            System.out.println("Connection to SQLite has been established.");

            return conn;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Book[] getAll(){
        Book[] books = new Book[100];
        try (PreparedStatement statement = this.connection.prepareStatement(this.selectStatement);
             ResultSet resultSet = statement.executeQuery()) {
            int i = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String bookStateString = resultSet.getString("state");
                bookState bookState = null;
                if(Objects.equals(bookStateString, "USED")) bookState= USED;
                if(Objects.equals(bookStateString, "DECENT")) bookState= DECENT;
                if(Objects.equals(bookStateString, "NEW")) bookState= NEW;
                String publisher = resultSet.getString("publisher");
                int year = resultSet.getInt("year");
                books[i]=new Book(id, title, author, publisher, year, bookState);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public Book[] getAvailable() {
        System.out.println("get available repo");
        String query = "SELECT * FROM books\n" +
                "WHERE id NOT IN (\n" +
                "    SELECT id FROM loans WHERE active = 1\n" +
                ")";

        Vector<Book> books = new Vector<>();
        try (PreparedStatement statement = this.connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            int i = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String author = resultSet.getString("author");
                String title = resultSet.getString("title");
                int year = resultSet.getInt("year");
                String state = resultSet.getString("state");
                bookState bookState = null;
                if (Objects.equals(state, "USED")) {
                    bookState = USED;
                } else if (Objects.equals(state, "NEW")) {
                    bookState = NEW;
                } else if (Objects.equals(state, "DECENT")) {
                    bookState = DECENT;
                }
                String publisher = resultSet.getString("publisher");

                //
                books.add(new Book(id, author, title, publisher, year, bookState));
                //
                i++;

            }
            System.out.println("returning no books: " + books.size());
            return books.toArray(new Book[books.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Book[] getUnavailable(String username) {
        System.out.println("get rented books repo");
        String query;
        if(Objects.equals(username, "LIBRARIAN")) {
            query = "SELECT b.*\n" +
                    "FROM books b\n" +
                    "JOIN loans l ON b.id = l.id\n" +
                    "WHERE l.active = 1";
        }
        else
        {
            query = "SELECT b.*\n" +
                    "FROM books b\n" +
                    "JOIN loans l ON b.id = l.id\n" +
                    "WHERE l.active = 1 and l.username = \"" + username + "\"";
        }
        Vector<Book> books = new Vector<>();
        try (PreparedStatement statement = this.connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            int i = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String author = resultSet.getString("author");
                String title = resultSet.getString("title");
                int year = resultSet.getInt("year");
                String state = resultSet.getString("state");
                bookState bookState = null;
                if (Objects.equals(state, "USED")) {
                    bookState = USED;
                } else if (Objects.equals(state, "NEW")) {
                    bookState = NEW;
                } else if (Objects.equals(state, "DECENT")) {
                    bookState = DECENT;
                }
                String publisher = resultSet.getString("publisher");

                //
                books.add(new Book(id, author, title, publisher, year, bookState));
                //

                i++;
            }
            System.out.println("returning no books: " + books.size());

            return books.toArray(new Book[books.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
