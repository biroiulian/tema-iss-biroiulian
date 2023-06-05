package iss.biblioteca;

import iss.biblioteca.Domain.User;
import iss.biblioteca.Domain.UserType;
import iss.biblioteca.Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Button LoginButton;
    public Button RegisterButton;
    public TextField UsernameTextField;
    public PasswordField PwPasswordField;
    public Label WelcomeLabel;
    public Label LoginStatusLabel;

    private Service serv;


    public LoginController(){
        try {
            this.initializeComponents();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Initialized Controller...");
    }

    private void initializeComponents() throws IOException {

    }

    public void onLoginButtonClicked() throws IOException {
        System.out.println("Login Clicked");
        User u=this.serv.getUser(UsernameTextField.getText(), PwPasswordField.getText());
        if(u==null) {
            LoginStatusLabel.setText("The Username-Password combination does not exist");
            return;
        }
        this.serv.setCurrentUser(u);

        //proceed to changing the scene
        FXMLLoader fxmlLoaderLogin = null;
        Parent root = null;

        if(u.getUserType()== UserType.LIBRARIAN) {
            fxmlLoaderLogin = new FXMLLoader(getClass().getResource("LibrarianHomeScene.fxml"));
            root = fxmlLoaderLogin.load();
            LibrarianHomeController librarianHomeCont = fxmlLoaderLogin.getController();
            librarianHomeCont.setService(serv);
            librarianHomeCont.initializeComponents();
        }
        else if (u.getUserType()== UserType.MEMBER) {
            fxmlLoaderLogin = new FXMLLoader(getClass().getResource("MemberHomeScene.fxml"));
            root = fxmlLoaderLogin.load();
            MemberHomeController memberHomeCont = fxmlLoaderLogin.getController();
            memberHomeCont.setServ(serv);
            memberHomeCont.initializeComponents();
        }
        else if (u.getUserType()== UserType.BANNED) {
            this.LoginStatusLabel.setText("You are banned. Speak with the nearest librarian.");
        }

        Scene HomeScene = new Scene(root);
        Stage Window = (Stage)LoginButton.getScene().getWindow();
        Window.setScene(HomeScene);

        //scene changed
        System.out.println("Scene changed to Main");
    }

    public void setService(Service service) {
        this.serv=service;
    }
}