package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onLoginButtonClick() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("main-view.fxml")));
        Stage window = (Stage) welcomeText.getScene().getWindow();
        window.setScene(new Scene(root, 700, 500));
    }

    public void onRegisterButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("register-view.fxml")));
        Stage window = (Stage) welcomeText.getScene().getWindow();
        window.setScene(new Scene(root, 700, 500));
    }
}
