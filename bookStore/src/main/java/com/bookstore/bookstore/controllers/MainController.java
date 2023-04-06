package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.Product;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.boot.archive.internal.UrlInputStreamAccess;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class MainController {
    @FXML
    public TilePane content;
    @FXML
    public TextField searchBar;
    @FXML
    private Label welcomeText;
    ArrayList<Product> data;

    @FXML
    protected void onLogOutButtonClick() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("login-view.fxml")));
        Stage window = (Stage) welcomeText.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
    }

    public void generateList(ArrayList<Product> filteredData){
        content.getChildren().clear();
        try {
            for (var item : filteredData) {
                BorderPane card = new BorderPane();
                card.getStyleClass().add("card");
                card.getStyleClass().add("card-effect");
                card.setMinSize(200,230);

                Label title = new Label(item.getName() + " (" + item.getProduction() + ")");
                title.setWrapText(true);
                title.setMaxWidth(160);
                title.setAlignment(Pos.CENTER);
                title.setTextAlignment(TextAlignment.CENTER);
                BorderPane.setAlignment(title, Pos.CENTER);
                title.setId("title");

                Label details = new Label("Rating: " + item.getReview() + "\n" + "Genre: " + item.getGenre() + "\n" + "Type: " + item.getType());
                details.setWrapText(true);
                details.setMaxWidth(160);
                details.setAlignment(Pos.CENTER);
                details.setTextAlignment(TextAlignment.CENTER);
                BorderPane.setAlignment(details, Pos.CENTER);
                details.setId("details");

                Image img = new Image(getClass().getResource("/com/bookstore/bookstore/image/book.png").toExternalForm());
                ImageView imageView = new ImageView();
                imageView.setImage(img);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(140);
                imageView.setId("img");

                Tooltip tooltip = new Tooltip(item.getName() + " (" + item.getProduction() + ")");
                Tooltip.install(imageView,tooltip);

                card.setTop(title);
                card.setCenter(imageView);
                card.setBottom(details);
                content.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void onUsersButtonClick(ActionEvent actionEvent) {
//        ArrayList<User> data = DAO.instance().runCustomQuery(User.class, "SELECT * FROM BOOK_STORE_USERS");
//        this.generateTable(data);
//    }

    @FXML
    public void onProductsButtonClick(ActionEvent actionEvent) {
        if (data == null) data = DAO.instance().runCustomQuery(Product.class, "SELECT * FROM BOOK_STORE_PRODUCT");
        this.generateList(data);
    }

    @FXML
    public void onSearch(ActionEvent actionEvent) {
        if (data == null) return;

        ArrayList<Product> filteredData = new ArrayList<>();
        for (var item:data) {
            if (item.getName().contains(searchBar.getText())) {
                filteredData.add(item);
            }
        }
        System.out.println(searchBar.getText());
        this.generateList(filteredData);
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
