package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

public class ProfileController {
    public HBox toolbar;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField name;
    @FXML
    private TextField birthday;
    @FXML
    private TextField email;
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
    private boolean isAdmin;

    @FXML
    public void initialize() {
        onProfileClicked();
    }

    @FXML
    public void onProfileClicked() {
        this.user = DAO.getCurrentUser();
        isAdmin = this.user.getEmail().equals("admin");
        if (isAdmin) {
            Button admin = new Button("Admin");
            admin.setOnAction(actionEvent -> {
                this.onAdminClicked();
            });
            admin.getStyleClass().addAll("nav-button", "raised");
            admin.setMinWidth(70);
            toolbar.getChildren().add(admin);
        }

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
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("login-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainApplication.getMainStage().getScene().setRoot(root);
        MainApplication.getMainStage().setMaximized(false);
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

    public void onPasswordSubmitClicked() {
        if (!passwordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty() && passwordField.getText().equals(confirmPasswordField.getText())) {
            user.setPassword(DigestUtils.sha256Hex(passwordField.getText()));
            DAO.instance().updateData(user);
        } else {
            System.out.println("Nem jo vagy nem egyeznek a jelszavak :$");
        }
    }

    public void onPersonalSubmitClicked() {
        if (name.getText().isEmpty() || email.getText().isEmpty() || birthday.getText().isEmpty()) {
            System.out.println("Nem jo");
        } else {
            user.setName(name.getText());
            user.setEmail(email.getText());
            user.setBirthDate(Date.valueOf(birthday.getText()));

            DAO.instance().updateData(user);
        }
    }

    public void onAdminClicked() {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("admin-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainApplication.getMainStage().getScene().setRoot(root);
        MainApplication.getMainStage().setMaximized(true);
    }
}
