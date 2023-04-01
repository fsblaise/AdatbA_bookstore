package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.Product;
import com.bookstore.bookstore.models.Purchase;
import com.bookstore.bookstore.models.Store;
import com.bookstore.bookstore.models.User;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXTooltip;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainController {
    @FXML
    public TilePane content;
    public HBox searchBar;
    @FXML
    private Label welcomeText;
    @FXML
    private TableView dataTable;
    private TableColumn[] columns;

    @FXML
    protected void onLogOutButtonClick() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("login-view.fxml")));
        Stage window = (Stage) welcomeText.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }

    public void generateList(ArrayList<Product> data){
//        dataTable.getColumns().clear();
//        dataTable.getItems().clear();
//        List<Field> properties = Arrays.asList(data.get(0).getClass().getDeclaredFields());
//        columns = new TableColumn[properties.size()];
//
//        for (int i = 0; i < columns.length; i++) {
//            columns[i] = new TableColumn<T, String>(properties.get(i).getName());
//            columns[i].setCellValueFactory(new PropertyValueFactory<>(properties.get(i).getName()));
//            dataTable.getColumns().add(columns[i]);
//        }
//
//        for (var item: data) {
//            dataTable.getItems().add(item);
//        }
        //content.setItems(FXCollections.observableList(data));
        try {
            for (var item : data) {
                BorderPane card = new BorderPane();
                card.setStyle("-fx-border-radius: 25; -fx-background-radius: 25; -fx-background-color: #ffffff; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 0);");
                card.setMinSize(200,250);

                Label label = new Label(item.getName() + " (" + item.getProduction() + ")");
                BorderPane.setAlignment(label, Pos.CENTER);

                Tooltip tooltip = new Tooltip("Rating: " + item.getReview() + "\n" + "Genre: " + item.getGenre() + "\n" + "Type: " + item.getType());
                tooltip.setShowDelay(Duration.seconds(0.3));
                Tooltip.install(label, tooltip);

                card.setBottom(label);
                content.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //content.getChildren().add()
    }

//    public void onUsersButtonClick(ActionEvent actionEvent) {
//        ArrayList<User> data = DAO.instance().runCustomQuery(User.class, "SELECT * FROM BOOK_STORE_USERS");
//        this.generateTable(data);
//    }

    public void onProductsButtonClick(ActionEvent actionEvent) {
        ArrayList<Product> data = DAO.instance().runCustomQuery(Product.class, "SELECT * FROM BOOK_STORE_PRODUCT");
        this.generateList(data);
    }

//    public void onPurchasesButtonClick(ActionEvent actionEvent) {
//        ArrayList<Purchase> data = DAO.instance().runCustomQuery(Purchase.class, "SELECT * FROM BOOK_STORE_PURCHASE");
//        this.generateTable(data);
//    }
//
//    public void onStoresButtonClick(ActionEvent actionEvent) {
//        ArrayList<Store> data = DAO.instance().runCustomQuery(Store.class, "SELECT * FROM BOOK_STORE_STORE");
//        this.generateTable(data);
//    }
}
