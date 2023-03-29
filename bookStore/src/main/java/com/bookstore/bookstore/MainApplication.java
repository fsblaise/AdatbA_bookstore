package com.bookstore.bookstore;

import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.*;
import com.bookstore.bookstore.sshconfig.SSHConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SSHConnector sshConnector;

        try {
            sshConnector = new SSHConnector();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        DAO.instance().addData(new User(1, new Date(), 0, "Alma@alma.com", "Alma", false));
        System.out.println(DAO.instance().getDataByID(User.class, 1));
        System.out.println(DAO.instance().runCustomQuery(User.class, "SELECT * FROM BOOK_STORE_USER"));
        DAO.instance().updateData(new User(1, new Date(), 0, "Alma@alma.com", "Korte", false));
        System.out.println(DAO.instance().runCustomQuery(User.class, "SELECT * FROM BOOK_STORE_USER"));
        DAO.instance().deleteData(new User(1, new Date(), 0, "Alma@alma.com", "Korte", false));
        System.out.println(DAO.instance().runCustomQuery(User.class, "SELECT * FROM BOOK_STORE_USER"));


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
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
