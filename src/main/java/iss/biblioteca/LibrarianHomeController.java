package iss.biblioteca;

import iss.biblioteca.Domain.Book;
import iss.biblioteca.Domain.User;
import iss.biblioteca.Domain.UserType;
import iss.biblioteca.Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class LibrarianHomeController {
    @FXML
    private Button BanButton;
    @FXML
    private Button BackButton;
    @FXML
    private Button ReturnButton;
    private ObservableList<User> showedUsers;
    private ObservableList<Book> showedBooks;
    @FXML
    private TableView<User> userView;
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableView<Book> bookView;
    private TableColumn<Book, Integer> idColumn;
    private TableColumn<Book, String> titleColumn;
    private TableColumn<Book, String> authorColumn;

    private Service service;

    public LibrarianHomeController(){

    }

    public void setService(Service service) {

        this.service = service;
    }
    @FXML
    public void initialize(){
        //*********
        usernameColumn = new TableColumn<>("USERNAME");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Add the columns to the TableView
        userView.getColumns().setAll(usernameColumn);

        // Create the ObservableList of User objects
        showedUsers = FXCollections.observableArrayList();
        userView.setItems(showedUsers);
        //
        userView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Handle single click
                User selectedUser = userView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    // Call your method with the selected book
                    handleRowSelection(selectedUser);
                }
            }
        });

        //********
        idColumn = new TableColumn<>("ID");
        titleColumn = new TableColumn<>("Title");
        authorColumn = new TableColumn<>("Author");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        // Add the columns to the TableView
        bookView.getColumns().setAll(idColumn, titleColumn, authorColumn);

        // Create the ObservableList of Book objects
        showedBooks = FXCollections.observableArrayList();
        bookView.setItems(showedBooks);

    }

    private void handleRowSelection(User selectedBook) {
        System.out.println(selectedBook);
    }

    public void initializeComponents() {
        showLateUsers();
        showBooks();
    }

    private void showBooks() {
        this.showedBooks.clear();
        this.showedBooks.addAll(service.getRentedBooks());
    }

    public void showLateUsers(){
        this.showedUsers.clear();
        this.showedUsers.addAll(service.getLateUsers());
    }

    public void onBanButtonClicked(){
        User selecteUser = userView.getSelectionModel().getSelectedItem();
        System.out.println(selecteUser);
        this.service.banUser(selecteUser);
        this.showLateUsers();
    }
    public void onReturnButtonClicked(){
        Book selecteBook = bookView.getSelectionModel().getSelectedItem();
        System.out.println(selecteBook);
        this.service.returnBook(selecteBook);
        this.showBooks();
        this.showLateUsers();
    }

    public void onBackButtonClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Parent newSceneRoot = fxmlLoader.load();
        Scene newScene = new Scene(newSceneRoot);

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
}
