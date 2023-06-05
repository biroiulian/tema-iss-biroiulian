package iss.biblioteca.Repository;

import iss.biblioteca.Domain.Book;
import iss.biblioteca.Domain.Loan;
import iss.biblioteca.Domain.User;
import iss.biblioteca.Domain.UserType;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.Vector;

public class LoanRepository {
    private String url;
    private Connection connection;
    private String selectStatement;
    public LoanRepository(String url){
        this.url = url;
        this.selectStatement = "SELECT * FROM loans";
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

    public boolean addLoan(Book b, String username, LocalDate return_date) {
        String query = "INSERT INTO loans (username, id, active, return_date, extended) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = this.connection.prepareStatement(query)){
            // Set values for the parameters in the SQL statement
            stmt.setInt(2, b.getId());  // book_id
            stmt.setString(1, username);  // username
            stmt.setInt(3, 1);  // active

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = return_date.format(format);
            stmt.setString(4, date);  // return_date

            stmt.setInt(5, 0); //extended
            // Execute the insert statement
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted <= 0) {
                throw new SQLException("nu a adaugat");
            }
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public Loan getLoan(int bookId, String username){
        Vector<Loan> elems = new Vector<>();

        String query = "SELECT * FROM loans WHERE id=? and username=?";

        try (PreparedStatement stmt = this.connection.prepareStatement(query)){

            stmt.setInt(1, bookId);
            stmt.setString(2, username);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String un = resultSet.getString("username");
                int active = resultSet.getInt("active");
                String date = resultSet.getString("return_date");
                int ext = resultSet.getInt("extended");

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                elems.add(new Loan(id, un, active, LocalDate.parse(date, format), ext));
            }

            return elems.elementAt(0);
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public String extendLoan(Book b, String username){
        Loan l = getLoan(b.getId(), username);
        if(l==null || l.getExtended()==1){
            return "";
        }

        String query = "UPDATE loans SET extended = 1, return_date = ? where id=? and username=?";

        try (PreparedStatement stmt = this.connection.prepareStatement(query)){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Set values for the parameters in the SQL statement
            LocalDate date = l.getDate();
            date = date.plusDays(10);
            String s_date = date.format(format);
            stmt.setString(1, s_date);
            stmt.setInt(2, b.getId());  // book_id
            stmt.setString(3, username);  // username
            System.out.println(stmt.executeUpdate());

            return s_date;
        }
        catch (SQLException e){
            e.printStackTrace();
            return "";
        }
    }

    public void endLoan(Book b){
        String query = "UPDATE loans SET active = 0 where id=? and active=1";

        try (PreparedStatement stmt = this.connection.prepareStatement(query)){

            // Set values for the parameters in the SQL statement
            stmt.setInt(1, b.getId());  // book_id
            System.out.println(stmt.executeUpdate());

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public User[] getLateUsers() {
        Vector<User> elems = new Vector<>();

        String query = "SELECT u.*, l.return_date FROM users u INNER JOIN loans l ON u.username = l.username WHERE l.active = 1";

        try (PreparedStatement stmt = this.connection.prepareStatement(query)){

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String pw = resultSet.getString("password");
                int age = resultSet.getInt("age");
                String type = resultSet.getString("type");
                UserType ut;
                if(Objects.equals(type, "LIBRARIAN"))
                    continue;
                else if(Objects.equals(type, "BANNED")){
                    continue;
                }
                else
                    ut = UserType.MEMBER;
                String un = resultSet.getString("username");
                String date = resultSet.getString("return_date");

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate dt = LocalDate.parse(date, format);
                if(dt.isBefore(LocalDate.now())){
                    System.out.println("before present date: " + dt);
                    elems.add(new User(pw, un, age, ut));
                }
            }

            return elems.toArray(new User[elems.size()]);
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
