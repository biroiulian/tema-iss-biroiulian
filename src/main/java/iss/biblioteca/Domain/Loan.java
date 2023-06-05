package iss.biblioteca.Domain;

import java.sql.Date;
import java.time.LocalDate;

public class Loan {
    private int id;
    private String username;
    private int active;
    private LocalDate date;
    private int extended;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Loan(int id, String username, int active, LocalDate date, int ext) {
        this.id = id;
        this.username = username;
        this.active = active;
        this.date = date;
        this.extended = ext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExtended() {
        return extended;
    }

    public void setExtended(int extended) {
        this.extended = extended;
    }
}
