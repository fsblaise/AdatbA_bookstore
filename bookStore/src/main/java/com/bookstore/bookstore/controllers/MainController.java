package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
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
    public void initialize() {
        onProductsButtonClick();
    }

    @FXML
    protected void onLogOutButtonClick() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("login-view.fxml")));
        Stage window = (Stage) welcomeText.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
    }

    public void generateList(ArrayList<Product> filteredData) {
        content.getChildren().clear();
        try {
            for (var item : filteredData) {
                BorderPane card = new BorderPane();
                card.getStyleClass().add("card");
                card.getStyleClass().add("card-effect");
                card.setMinSize(200, 230);

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

                Image img = new Image(Objects.requireNonNull(getClass().getResource("/com/bookstore/bookstore/image/book.png")).toExternalForm());
                ImageView imageView = new ImageView();
                imageView.setImage(img);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(140);
                imageView.setId("img");

                Tooltip tooltip = new Tooltip(item.getName() + " (" + item.getProduction() + ")");
                Tooltip.install(imageView, tooltip);

                card.setTop(title);
                card.setCenter(imageView);
                card.setBottom(details);

                Button button = new Button();
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> DAO.cart.put(item.getId(), DAO.cart.get(item.getId()) == null ? 1 : DAO.cart.get(item.getId()) + 1));
                button.setGraphic(card);
                button.setMinSize(200, 230);
                button.setMaxSize(200, 230);
                button.getStyleClass().add("card");
                button.getStyleClass().add("card-effect");

                content.getChildren().add(button);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onProductsButtonClick() {
        if (data == null)
            data = DAO.instance().runCustomQuery(Product.class, "SELECT * FROM BOOK_STORE_PRODUCT ORDER BY id ASC, production DESC, review DESC");
        this.generateList(data);
    }

    @FXML
    public void onSearch() {
        this.generateList(DAO.instance().searchProduct(searchBar.getText()));
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

        Stage window = (Stage) welcomeText.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
    }
}
