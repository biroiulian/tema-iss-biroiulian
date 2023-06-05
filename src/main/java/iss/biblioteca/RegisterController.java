package iss.biblioteca;

import iss.biblioteca.Domain.User;
import iss.biblioteca.Domain.UserType;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import iss.biblioteca.Service.Service;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class RegisterController {
    public Button RegisterButton;
    public Button BackToLoginButton;
    public PasswordField PasswordTextField;
    public TextField UsernameTextField;
    public TextField AgeTextField;
    public TextField NameTextField;
    public TextField AdressTextField;
    public TextField CnpTextField;
    public TextField PhoneTextField;
    public Text StatusText;

    private Service serv;

    public RegisterController(){
        try {
            this.initializeComponents();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Initialized Controller...");
    }

    private void initializeComponents() throws IOException {

    }

    public void setService(Service service) {
        this.serv=service;
    }

    public void onBackToLoginButtonClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        System.out.println("Back to Login Clicked");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IdleScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        IdleController idleCont = fxmlLoader.getController();
        idleCont.setService(serv);

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.show();
        System.out.println("Scene changed to Login");
    }


    public void onRegisterButtonClicked(javafx.event.ActionEvent actionEvent) {
        this.UsernameTextField.getText();
        try {
            this.serv.register(new User(
                    this.PasswordTextField.getText(),
                    this.UsernameTextField.getText(),
                    Integer.parseInt(this.AgeTextField.getText()),
                    this.AdressTextField.getText(),
                    this.PhoneTextField.getText(),
                    this.CnpTextField.getText(),
                    this.NameTextField.getText(),
                    UserType.MEMBER
            ));
            this.StatusText.setText("Successfully registered! Go to login page!");
        }
        catch (Exception e){
            this.StatusText.setText("Ups... ceva n-a functionat!\nIncearca, in schimb, sa te loghezi cu \ndatele pe care le-ai introdus.");
        }
    }
}
