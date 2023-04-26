package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.Store;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class StoresController {
    @FXML
    public Label welcomeText;
    @FXML
    public AnchorPane map;
    @FXML
    public Label storeAddress;
    public HBox toolbar;
    private boolean isAdmin;
    private ArrayList<Store> stores = new ArrayList<>();

    @FXML
    public void initialize() {
        onStoresClicked();
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

    public void onStoresClicked() {
        isAdmin = DAO.getCurrentUser().getEmail().equals("admin");
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
        stores = DAO.instance().runCustomQuery(Store.class,"SELECT * FROM BOOK_STORE_STORE");
        for (var store:
                stores) {
            String[] split = store.getCoords().split("\\|");
            if (split[0].equals("0") && split[1].equals("0")) continue;
            Button b = new Button();
            b.setOnAction(actionEvent -> {
                this.storeAddress.setText(store.getPlace());
            });
            Image img = new Image(getClass().getResource("/com/bookstore/bookstore/image/pin.png").toExternalForm());
            ImageView buttonImg = new ImageView(img);
            buttonImg.setFitHeight(24);
            buttonImg.setPreserveRatio(true);

            b.setLayoutX(Double.parseDouble(split[0]));
            b.setLayoutY(Double.parseDouble(split[1]));
            b.setGraphic(buttonImg);
            b.setMaxSize(24,24);
            b.setId("pin");

            map.getChildren().add(b);
        }
    }

    public void onProfileClicked(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("profile-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainApplication.getMainStage().getScene().setRoot(root);
    }

    public void onMapClicked(MouseEvent mouseEvent) {
        if (!isAdmin) return;
        Dialog form = new Dialog();
        ButtonType submit = new ButtonType("Submit");
        form.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, submit);
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();

        Label capacityLabel = new Label("Capacity:");
        Spinner<Integer> capacitySpinner = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory=
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50000, 0);
        capacitySpinner.setValueFactory(valueFactory);
        capacitySpinner.setEditable(true);

        form.getDialogPane().setContent(new VBox(addressLabel,addressField,capacityLabel,capacitySpinner));

        Optional<ButtonType> result = form.showAndWait();
        if (result.isPresent() && result.get().equals(submit)){
            if (addressField.getText().equals("") || capacitySpinner.getValue().equals(0)) return;

            Button b = new Button();
            Image img = new Image(getClass().getResource("/com/bookstore/bookstore/image/pin.png").toExternalForm());
            ImageView buttonImg = new ImageView(img);
            buttonImg.setFitHeight(24);
            buttonImg.setPreserveRatio(true);

            b.setLayoutX(mouseEvent.getX()-20);
            b.setLayoutY(mouseEvent.getY()-12);
            b.setGraphic(buttonImg);
            b.setMaxSize(24,24);
            b.setId("pin");

            map.getChildren().add(b);
            // add new store to the db
            String coords = (mouseEvent.getX()-20) + "|" + (mouseEvent.getY()-12);
            Store store = new Store();
            store.setCapacity(capacitySpinner.getValue());
            store.setPlace(addressField.getText());
            store.setType("fizikai");
            store.setCoords(coords);

            DAO.instance().addData(store);
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
