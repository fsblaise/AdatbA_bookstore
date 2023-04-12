package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class RegisterController {
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private DatePicker date;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onLoginButtonClick() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("login-view.fxml")));
        Stage window = (Stage) welcomeText.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
    }

    public void onRegisterButtonClick() throws IOException {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        String nameString = name.getText();
        String passwordString = password.getText();
        String confirmPasswordString = confirmPassword.getText();
        String emailString = email.getText();
        Date birth = Date.from(date.getValue().atStartOfDay(defaultZoneId).toInstant());

        if (nameString.isEmpty() || passwordString.isEmpty() || confirmPasswordString.isEmpty() || emailString.isEmpty() || !passwordString.equals(confirmPasswordString)) {
            //TODO: Error
            return;
        }

        User user = new User();

        user.setName(nameString);
        user.setEmail(emailString);
        user.setBirthDate(birth);
        user.setPassword(DigestUtils.sha256Hex(passwordString));
        user.setPurchasedProducts(0);
        user.setIsRegular(false);

        DAO.instance().addData(user);

        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("login-view.fxml")));
        Stage window = (Stage) welcomeText.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
    }
}
