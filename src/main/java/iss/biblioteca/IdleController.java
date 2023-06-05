package iss.biblioteca;

import iss.biblioteca.Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class IdleController {
    @FXML
    Button LoginButton;
    @FXML
    Button RegisterButton;

    Service serv;

    public void onLoginButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Parent newSceneRoot = fxmlLoader.load();
        Scene newScene = new Scene(newSceneRoot);

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(serv);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    public void onRegisterButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterScene.fxml"));
        Parent newSceneRoot = fxmlLoader.load();
        Scene newScene = new Scene(newSceneRoot);

        RegisterController registerController = fxmlLoader.getController();
        registerController.setService(serv);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();

    }

    public void setService(Service s) {
        this.serv = s;
    }
}
