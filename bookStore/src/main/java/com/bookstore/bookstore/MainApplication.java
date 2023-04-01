package com.bookstore.bookstore;

import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.sshconfig.SSHConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
        SSHConnector sshConnector;

        try {
            sshConnector = new SSHConnector();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        DAO.instance();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("BookShop");
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            DAO.instance().closeSession();
            sshConnector.closeSSH();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
