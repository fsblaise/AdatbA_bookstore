package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private PasswordField password;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField email;


    @FXML
    protected void onLoginButtonClick() throws IOException {
        String emailString = email.getText();
        String passwordString = password.getText();

        if (emailString.isEmpty() || passwordString.isEmpty()) {
            //TODO: Error
            return;
        }

        if (emailString.equals("admin") && passwordString.equals("admin")) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("admin-view.fxml")));
            Stage window = (Stage) welcomeText.getScene().getWindow();
            window.setScene(new Scene(root, 700, 500));
            window.setMaximized(true);
            return;
        }

        DAO.instance().loginUser(emailString, passwordString);

        if (DAO.getCurrentUser() == null) {
            //TODO: Error
            return;
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("main-view.fxml")));
        MainApplication.getMainStage().getScene().setRoot(root);
        MainApplication.getMainStage().setMaximized(true);
    }

    public void onRegisterButtonClick() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("register-view.fxml")));
        Stage window = (Stage) welcomeText.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
    }
}
