package iss.biblioteca.Service;

import iss.biblioteca.Domain.Book;
import iss.biblioteca.Domain.Loan;
import iss.biblioteca.Domain.User;
import iss.biblioteca.Domain.UserType;
import iss.biblioteca.Repository.BookRepository;
import iss.biblioteca.Repository.LoanRepository;
import iss.biblioteca.Repository.UserRepository;

import java.time.LocalDate;
import java.util.Objects;


public class Service {
    public static final String BD_URL = "jdbc:sqlite:C:\\Users\\Iulian\\IdeaProjects\\temeMPP\\Biblioteca\\src\\main\\resources\\library";
    private User currentUser;
    private UserRepository userRepo;
    private BookRepository bookRepo;
    private LoanRepository loanRepo;

    public Service() throws Exception {
        this.userRepo = new UserRepository(BD_URL);
        this.bookRepo = new BookRepository(BD_URL);
        this.loanRepo = new LoanRepository(BD_URL);

    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public User getUser(String un, String pw) {
        return userRepo.getElem(un, pw);
    }
    public Book[] getAvailableBooks() {
        return bookRepo.getAvailable();
    }
    public Book[] getRentedBooks() {
        if(currentUser.getUserType()== UserType.LIBRARIAN){
            return bookRepo.getUnavailable("LIBRARIAN");
        }
        else
            return bookRepo.getUnavailable(currentUser.getUsername());
    }
    public String rentBook(Book b){
        if (this.loanRepo.addLoan(b, currentUser.getUsername(), LocalDate.now().plusDays(30))){
            return "Successfully rented! To be \nreturned on " + LocalDate.now().plusDays(30);
        }
        else return "Unsuccessfully rented!\n It seems like something \nwent wrong! Come back later!";
    }
    public String extendRent(Book selectedBook) {
        String date = this.loanRepo.extendLoan(selectedBook, currentUser.getUsername());
        if (!Objects.equals(date, "")){
            return "\nSuccessfully extended! \nNew return date " + date;
        }
        else return "\nUnsuccessfully extended! \nIt seems like something\nwent wrong! Come back later!";
    }
    public User[] getLateUsers() {
        return this.loanRepo.getLateUsers();
    }
    public void banUser(User selecteUser) {
        this.userRepo.ban(selecteUser);
    }
    public void returnBook(Book book){
        this.loanRepo.endLoan(book);
    }
    public void register(User user) {
        //make validations etc etc
        try {
            this.userRepo.add(user);
        }
        catch(Exception e){
            throw e;
        }
    }
}
