package iss.biblioteca;

import iss.biblioteca.Domain.Book;
import iss.biblioteca.Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static iss.biblioteca.Domain.bookState.DECENT;

public class MemberHomeController {
    private Service serv;
    private ObservableList<Book> showedBooks;
    public Button RentedButton;
    public Button AvailableButton;
    public Button ConfirmButton;
    public Button BackButton;
    public Label StateLabel;
    public Label YearLabel;
    public Label PublisherLabel;
    public Label StatusLabel;


    @FXML
    private TableView<Book> tableView;
    private TableColumn<Book, Integer> idColumn;
    private TableColumn<Book, String> titleColumn;
    private TableColumn<Book, String> authorColumn;

    public MemberHomeController(){

    }

    public void setServ(Service serv) {
        this.serv = serv;
    }
    @FXML
    public void initialize() {
        idColumn = new TableColumn<>("ID");
        titleColumn = new TableColumn<>("Title");
        authorColumn = new TableColumn<>("Author");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        // Add the columns to the TableView
        tableView.getColumns().setAll(idColumn, titleColumn, authorColumn);

        // Create the ObservableList of Book objects
        showedBooks = FXCollections.observableArrayList();
        tableView.setItems(showedBooks);

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Handle single click
                Book selectedBook = tableView.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    // Call your method with the selected book
                    handleRowSelection(selectedBook);
                }
            }
        });
    }

    private void handleRowSelection(Book selectedBook) {
        this.YearLabel.setText("Year " + selectedBook.getYear());
        this.PublisherLabel.setText("Publisher " + selectedBook.getPublisher());
        this.StateLabel.setText("State "+ selectedBook.getBookState().toString());
        if(Objects.equals(this.ConfirmButton.getText(), "+10 days")){
            this.StatusLabel.setText("Confirmation status: " + "to return");
        }
        else
            this.StatusLabel.setText("Confirmation status: ");
    }

    public void initializeComponents() {
        showAvailableBooks();
    }

    private void showAvailableBooks(){
        this.showedBooks.clear();
        this.showedBooks.addAll(serv.getAvailableBooks());
        this.tableView.setItems(this.showedBooks);
        System.out.println("size of showed list " + showedBooks.size());
    }
    private void showRentedBooks(){
        this.showedBooks.clear();
        this.showedBooks.addAll(serv.getRentedBooks());
        this.tableView.setItems(this.showedBooks);
        System.out.println("size of showed list " + showedBooks.size());
    }
    public void onRentedButtonClicked(){
        this.showRentedBooks();
        this.ConfirmButton.setText("+10 days");
    }
    public void onAvailableButtonClicked(){
        this.showAvailableBooks();
        this.ConfirmButton.setText("Confirm Loan");
    }
    public void onBackButtonClicked(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IdleScene.fxml"));
        Parent newSceneRoot = fxmlLoader.load();
        Scene newScene = new Scene(newSceneRoot);

        IdleController idleController = fxmlLoader.getController();
        idleController.setService(serv);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
    public void onConfirmButtonClicked(){
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            if (Objects.equals(this.ConfirmButton.getText(), "+10 days")) {
                this.StatusLabel.setText("Confirmation Status: " + serv.extendRent(selectedBook));
            }
            else {
                this.StatusLabel.setText("Confirmation Status: \n" + this.serv.rentBook(selectedBook));
                this.showAvailableBooks();
            }
        }
    }

}
