package com.bookstore.bookstore;

import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.sshconfig.SSHConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;


public class MainApplication extends Application {
    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        SSHConnector sshConnector;

        try {
            sshConnector = new SSHConnector();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        DAO.instance();

        mainStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        mainStage.setTitle("BookShop");
        mainStage.setScene(scene);
        mainStage.setOnCloseRequest(windowEvent -> {
            DAO.instance().closeSession();
            sshConnector.closeSSH();
        });
        mainStage.show();
    }

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
        launch();
    }
}
