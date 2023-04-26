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
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

public class ProfileController {
    public HBox toolbar;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField name;
    @FXML
    private DatePicker birthday;
    @FXML
    private TextField email;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
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
        DAO.instance().updateUser();
        onProfileClicked();
    }

    @FXML
    public void onProfileClicked() {
        this.user = DAO.getCurrentUser();
        isAdmin = this.user.getEmail().equals("admin");
        if (isAdmin) {
            boolean hasAdminButton = false;
            for (var item:
                 toolbar.getChildren()) {
                if (item.getId() != null && item.getId().equals("admin")) {
                    hasAdminButton = true;
                }
            }
            if(!hasAdminButton) {
                Button admin = new Button("Admin");
                admin.setId("admin");
                admin.setOnAction(actionEvent -> {
                    this.onAdminClicked();
                });
                admin.getStyleClass().addAll("nav-button", "raised");
                admin.setMinWidth(70);
                toolbar.getChildren().add(admin);
            }
        }

        name.setText(user.getName());
        birthday.setValue(LocalDate.ofInstant(new java.util.Date(user.getBirthDate().getTime()).toInstant(), ZoneId.systemDefault()));
        email.setText(user.getEmail());
        purchaseCount.setText(String.valueOf(user.getPurchasedProducts()));
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
        if (name.getText().isEmpty() || email.getText().isEmpty()) {
            System.out.println("Nem jo");
        } else {
            user.setName(name.getText());
            user.setEmail(email.getText());
            user.setBirthDate(Date.valueOf(birthday.getValue()));

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
