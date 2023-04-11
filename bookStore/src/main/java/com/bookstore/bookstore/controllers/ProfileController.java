package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ProfileController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label name;
    @FXML
    private Label birthday;
    @FXML
    private Label email;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private Button submitButton;
    @FXML
    private Label purchaseCount;
    @FXML
    private Label isRegular;
    private User user;

    @FXML
    public void initialize() {
        onProfileClicked();
    }

    @FXML
    public void onProfileClicked() {
        this.user = DAO.getCurrentUser();

        name.setText(user.getName());
        birthday.setText(user.getBirthDate().toString());
        email.setText(user.getEmail());
        purchaseCount.setText("" + user.getPurchasedProducts());
        isRegular.setText(user.getIsRegular() ? "Yes" : "No");
    }

    @FXML
    public void onProductsButtonClick(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("main-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainApplication.getMainStage().getScene().setRoot(root);
    }

    @FXML
    public void onBasketClicked() {
        if (DAO.cart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("You have no items in your basket");
            alert.setContentText("Please add some items to your basket");
            alert.showAndWait();
            return;
        }

        Parent root;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("checkout-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainApplication.getMainStage().getScene().setRoot(root);
    }

    @FXML
    protected void onLogOutButtonClick() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("login-view.fxml")));
        Stage window = (Stage) welcomeText.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
    }

    @FXML
    public void onStoresClicked(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("stores-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainApplication.getMainStage().getScene().setRoot(root);
    }

    public void onSubmitClicked() {
        if (!passwordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty() && passwordField.getText().equals(confirmPasswordField.getText())) {
            System.out.println(passwordField.getText());
            System.out.println(confirmPasswordField.getText());
        } else {
            System.out.println("Nem jo vagy nem egyeznek a jelszavak :$");
        }
    }
}
