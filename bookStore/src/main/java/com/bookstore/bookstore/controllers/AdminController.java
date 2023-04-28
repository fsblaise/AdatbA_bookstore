package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class AdminController {
    @FXML
    private VBox content;
    @FXML
    private Button addProduct;
    private List<Node> items;

    @FXML
    protected void onAddProduct() {
        if (items != null) {
            content.getChildren().removeAll(items);
        }

        items = new ArrayList<>();

        //TODO: labels

        TextField name = new TextField();
        name.setLayoutY(150);

        TextField genre = new TextField();
        genre.setLayoutY(200);

        TextField type = new TextField();
        type.setLayoutY(250);

        TextField production = new TextField();
        production.setLayoutY(300);

        Button done = new Button();
        done.setText("done");
        done.setLayoutY(350);
        done.setOnAction(actionEvent -> {
            String nameString = name.getText();
            String genreString = genre.getText();
            String typeString = type.getText();
            String productionString = production.getText();

            if (nameString.isEmpty() || genreString.isEmpty() || typeString.isEmpty() || productionString.isEmpty()) {
                // TODO Error
                return;
            }

            Product product = new Product();
            product.setName(nameString);
            product.setGenre(genreString);
            product.setType(typeString);
            product.setProduction(productionString);
            product.setReview(0);

            DAO.instance().addData(product);

            content.getChildren().removeAll(items);
            items = null;
        });

        items.add(name);
        items.add(genre);
        items.add(type);
        items.add(production);
        items.add(done);

        content.getChildren().addAll(items);
    }

    @FXML
    protected void onAddStore() {
        if (items != null) {
            content.getChildren().removeAll(items);
        }

        items = new ArrayList<>();
        //TODO: labels

        TextField place = new TextField();
        place.setLayoutY(150);

        TextField capacity = new TextField();
        capacity.setLayoutY(200);

        Button done = new Button();
        done.setText("done");
        done.setLayoutY(250);
        done.setOnAction(actionEvent -> {
            String placeString = place.getText();
            int capacityInt = Integer.parseInt(capacity.getText());

            if (placeString.isEmpty()) {
                // TODO Error
                return;
            }

            Store store = new Store();
            store.setPlace(placeString);
            store.setCapacity(capacityInt);
            store.setType("fizikai");

            DAO.instance().addData(store);

            content.getChildren().removeAll(items);
            items = null;
        });

        items.add(place);
        items.add(capacity);
        items.add(done);

        content.getChildren().addAll(items);
    }

    @FXML
    protected void onModifyProduct() {
        String result = getDataFromDialog();

        if (result.isEmpty()) {
            // TODO: error
            return;
        }

        Product product = DAO.instance().getDataByID(Product.class, Integer.parseInt(result));

        if (items != null) {
            content.getChildren().removeAll(items);
        }

        items = new ArrayList<>();

        //TODO: labels
        TextField name = new TextField();
        name.setText(product.getName());
        name.setLayoutY(150);

        TextField genre = new TextField();
        genre.setText(product.getGenre());
        genre.setLayoutY(200);

        TextField type = new TextField();
        type.setText(product.getType());
        type.setLayoutY(250);

        TextField production = new TextField();
        production.setText(product.getProduction());
        production.setLayoutY(300);

        Button done = new Button();
        done.setText("done");
        done.setLayoutY(350);
        done.setOnAction(actionEvent -> {
            String nameString = name.getText();
            String genreString = genre.getText();
            String typeString = type.getText();
            String productionString = production.getText();

            if (nameString.isEmpty() || genreString.isEmpty() || typeString.isEmpty() || productionString.isEmpty()) {
                // TODO Error
                return;
            }

            product.setName(nameString);
            product.setGenre(genreString);
            product.setType(typeString);
            product.setProduction(productionString);

            DAO.instance().updateData(product);

            content.getChildren().removeAll(items);
            items = null;
        });

        items.add(name);
        items.add(genre);
        items.add(type);
        items.add(production);
        items.add(done);

        content.getChildren().addAll(items);
    }

    @FXML
    protected void onModifyStore() {
        String result = getDataFromDialog();

        if (result.isEmpty()) {
            // TODO: error
            return;
        }

        Store store = DAO.instance().getDataByID(Store.class, Integer.parseInt(result));

        if (items != null) {
            content.getChildren().removeAll(items);
        }

        items = new ArrayList<>();

        //TODO: labels
        TextField place = new TextField();
        place.setText(store.getPlace());
        place.setLayoutY(150);

        TextField capacity = new TextField();
        capacity.setText(String.valueOf(store.getCapacity()));
        capacity.setLayoutY(200);

        Button done = new Button();
        done.setText("done");
        done.setLayoutY(250);
        done.setOnAction(actionEvent -> {
            String placeString = place.getText();
            int capacityInt = Integer.parseInt(capacity.getText());

            if (placeString.isEmpty()) {
                // TODO Error
                return;
            }

            store.setPlace(placeString);
            store.setCapacity(capacityInt);

            DAO.instance().updateData(store);

            content.getChildren().removeAll(items);
            items = null;
        });

        items.add(place);
        items.add(capacity);
        items.add(done);

        content.getChildren().addAll(items);
    }

    private String getDataFromDialog() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("get ID");
        dialog.setHeaderText("Please enter the id");
        dialog.setContentText("ID:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    public void onDeleteProduct() {
        String idString = getDataFromDialog();

        if (idString.isEmpty()) {
            //TODO error
            return;
        }

        DAO.instance().deleteProduct(Integer.parseInt(idString));
    }

    public void onDeleteStore() {
        String idString = getDataFromDialog();

        if (idString.isEmpty()) {
            //TODO error
            return;
        }

        DAO.instance().deleteStore(Integer.parseInt(idString));
    }

    public <T> void generateTable(ArrayList<T> data) {
        if (items != null) {
            content.getChildren().removeAll(items);
        }

        items = new ArrayList<>();
        TableView<T> dataTable = new TableView<>();

        dataTable.getColumns().clear();
        dataTable.getItems().clear();
        List<Field> properties = Arrays.asList(data.get(0).getClass().getDeclaredFields());
        TableColumn[] columns = new TableColumn[properties.size()];

        dataTable.setPrefWidth(columns.length * 150);
        dataTable.setPrefHeight(data.size() * 26);

        for (int i = 0; i < columns.length; i++) {
            columns[i] = new TableColumn<T, String>(properties.get(i).getName());
            columns[i].setCellValueFactory(new PropertyValueFactory<>(properties.get(i).getName()));
            dataTable.getColumns().add(columns[i]);
        }

        for (var item : data) {
            dataTable.getItems().add(item);
        }

        items.add(dataTable);

        content.getChildren().addAll(items);
    }

    public void onUsersButtonClick() {
        ArrayList<User> data = DAO.instance().runCustomQuery(User.class, "SELECT * FROM BOOK_STORE_USERS");
        this.generateTable(data);
    }

    public void onProductsButtonClick() {
        ArrayList<Product> data = DAO.instance().runCustomQuery(Product.class, "SELECT * FROM BOOK_STORE_PRODUCT");
        this.generateTable(data);
    }

    public void onPurchasesButtonClick() {
        ArrayList<Purchase> data = DAO.instance().runCustomQuery(Purchase.class, "SELECT * FROM BOOK_STORE_PURCHASE");
        this.generateTable(data);
    }

    public void onStoresButtonClick() {
        ArrayList<Store> data = DAO.instance().runCustomQuery(Store.class, "SELECT * FROM BOOK_STORE_STORE");
        this.generateTable(data);
    }

    public void onAddStock() {
        if (items != null) {
            content.getChildren().removeAll(items);
        }

        items = new ArrayList<>();
        //TODO: labels

        TextField sum = new TextField();
        sum.setLayoutY(150);

        TextField capacity = new TextField();
        capacity.setLayoutY(200);

        ComboBox<Store> stores = new ComboBox<>();
        stores.getItems().addAll(DAO.instance().runCustomQuery(Store.class, "SELECT * FROM BOOK_STORE_STORE"));
        stores.setLayoutY(250);

        ComboBox<Product> product = new ComboBox<>();
        product.getItems().addAll(DAO.instance().runCustomQuery(Product.class, "SELECT * FROM BOOK_STORE_PRODUCT"));
        product.setLayoutY(300);

        Button done = new Button();
        done.setText("done");
        done.setLayoutY(350);
        done.setOnAction(actionEvent -> {
            int sumInt = Integer.parseInt(sum.getText());
            int capacityInt = Integer.parseInt(capacity.getText());
            Store store = stores.getSelectionModel().getSelectedItem();
            Stock stock = new Stock();

            stock.setSum(sumInt);
            stock.setCapacity(capacityInt);
            stock.setStore(store);
            stock.setProduct(product.getSelectionModel().getSelectedItem());
            stock.setIsLow(0);

            DAO.instance().addData(stock);

            content.getChildren().removeAll(items);
            items = null;
        });

        items.add(sum);
        items.add(capacity);
        items.add(stores);
        items.add(product);
        items.add(done);

        content.getChildren().addAll(items);
    }

    public void onModifyStock() {
        String result = getDataFromDialog();

        if (result.isEmpty()) {
            // TODO: error
            return;
        }

        Stock stock = DAO.instance().getDataByID(Stock.class, Integer.parseInt(result));

        if (items != null) {
            content.getChildren().removeAll(items);
        }

        items = new ArrayList<>();

        //TODO: labels
        TextField sum = new TextField();
        sum.setText(String.valueOf(stock.getSum()));
        sum.setLayoutY(150);

        TextField capacity = new TextField();
        capacity.setText(String.valueOf(stock.getCapacity()));
        capacity.setLayoutY(200);

        ComboBox<Store> stores = new ComboBox<>();
        stores.getItems().addAll(DAO.instance().runCustomQuery(Store.class, "SELECT * FROM BOOK_STORE_STORE"));
        stores.getSelectionModel().select(stock.getStore());
        stores.setLayoutY(250);

        TextField product = new TextField();
        product.setText(stock.getProduct().getName());
        product.setDisable(true);
        product.setLayoutY(300);

        CheckBox isLow = new CheckBox();
        isLow.setSelected(stock.getIsLow() == 1);
        isLow.setDisable(true);
        isLow.setLayoutY(350);

        Button done = new Button();
        done.setText("done");
        done.setLayoutY(400);
        done.setOnAction(actionEvent -> {
            int sumInt = Integer.parseInt(sum.getText());
            int capacityInt = Integer.parseInt(capacity.getText());
            Store store = stores.getSelectionModel().getSelectedItem();

            stock.setSum(sumInt);
            stock.setCapacity(capacityInt);
            stock.setStore(store);

            DAO.instance().updateData(stock);

            content.getChildren().removeAll(items);
            items = null;
        });

        items.add(sum);
        items.add(capacity);
        items.add(stores);
        items.add(product);
        items.add(isLow);
        items.add(done);

        content.getChildren().addAll(items);
    }

    public void onDeleteStock() {
        String idString = getDataFromDialog();

        if (idString.isEmpty()) {
            //TODO error
            return;
        }

        DAO.instance().deleteData(DAO.instance().getDataByID(Stock.class, Integer.parseInt(idString)));
    }

    public void onStocksButtonClick() {
        ArrayList<Stock> data = DAO.instance().runCustomQuery(Stock.class, "SELECT * FROM BOOK_STORE_STOCK");
        this.generateTable(data);
    }

    // Navibar section
    public void onProductsNavClick(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("main-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainApplication.getMainStage().getScene().setRoot(root);
    }

    public void onStoresClicked(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("stores-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainApplication.getMainStage().getScene().setRoot(root);
    }

    public void onBasketClicked(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("checkout-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainApplication.getMainStage().getScene().setRoot(root);
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

    public void onLogOutButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("login-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainApplication.getMainStage().getScene().setRoot(root);
        MainApplication.getMainStage().setMaximized(false);
    }
}
