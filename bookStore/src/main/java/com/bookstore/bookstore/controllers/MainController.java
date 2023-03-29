package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.Product;
import com.bookstore.bookstore.models.Purchase;
import com.bookstore.bookstore.models.Store;
import com.bookstore.bookstore.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private TableView dataTable;
    private TableColumn[] columns;
    @FXML
    protected void onLogOutButtonClick() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("login-view.fxml")));
        Stage window = (Stage) welcomeText.getScene().getWindow();
        window.setScene(new Scene(root, 700, 500));
    }

    public<T> void generateTable(ArrayList<T> data){
        dataTable.getColumns().clear();
        dataTable.getItems().clear();
        List<Field> properties = Arrays.asList(data.get(0).getClass().getDeclaredFields());
        columns = new TableColumn[properties.size()];

        for (int i = 0; i < columns.length; i++) {
            columns[i] = new TableColumn<T, String>(properties.get(i).getName());
            columns[i].setCellValueFactory(new PropertyValueFactory<>(properties.get(i).getName()));
            dataTable.getColumns().add(columns[i]);
        }

        for (var item: data) {
            dataTable.getItems().add(item);
        }
    }

    public void onUsersButtonClick(ActionEvent actionEvent) {
        ArrayList<User> data = DAO.instance().runCustomQuery(User.class, "SELECT * FROM BOOK_STORE_USER");
        this.generateTable(data);
    }

    public void onProductsButtonClick(ActionEvent actionEvent) {
        ArrayList<Product> data = DAO.instance().runCustomQuery(Product.class, "SELECT * FROM BOOK_STORE_PRODUCT");
        this.generateTable(data);
    }

    public void onPurchasesButtonClick(ActionEvent actionEvent) {
        ArrayList<Purchase> data = DAO.instance().runCustomQuery(Purchase.class, "SELECT * FROM BOOK_STORE_PURCHASE");
        this.generateTable(data);
    }

    public void onStoresButtonClick(ActionEvent actionEvent) {
        ArrayList<Store> data = DAO.instance().runCustomQuery(Store.class, "SELECT * FROM BOOK_STORE_STORE");
        this.generateTable(data);
    }
}
