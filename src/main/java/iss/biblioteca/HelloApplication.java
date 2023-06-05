package iss.biblioteca;

import iss.biblioteca.Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IdleScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        IdleController idleCont = fxmlLoader.getController();
        Service serv = new Service();
        idleCont.setService(serv);
        stage.setTitle("Biblioteca");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}