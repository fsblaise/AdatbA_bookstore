package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainController {
    @FXML
    public TilePane content;
    @FXML
    public TextField searchBar;
    @FXML
    public ComboBox<String> searchOpts;
    @FXML
    public Label searchRes;
    @FXML
    public VBox left;
    @FXML
    public VBox right;
    @FXML
    private Label welcomeText;
    @FXML
    ArrayList<RadioButton> typeRadios = new ArrayList<>();
    @FXML
    ArrayList<Button> genreButtons = new ArrayList<>();
    @FXML
    ArrayList<Button> storeButtons = new ArrayList<>();
    ArrayList<Product> data;

    @FXML
    public void initialize() throws NoSuchFieldException {
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
        ArrayList<Product> tmp = new ArrayList<>(filteredData);
        content.getChildren().clear();
        for (var radio:
             typeRadios) {
            String[] type = radio.getText().split(":");
            if (!radio.isSelected()) {
                tmp.removeIf(item -> item.getType().equals(type[0].strip()));
            }
        }
        this.searchRes.setText("Search results: " + tmp.size());
        try {
            for (var item : tmp) {
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
    public void onProductsButtonClick() throws NoSuchFieldException {
        data = DAO.instance().runCustomQuery(Product.class, "SELECT * FROM BOOK_STORE_PRODUCT ORDER BY production DESC, review DESC");
        List<Object[]> types = DAO.instance().listTypes();

        genreButtons.clear();
        typeRadios.clear();
        left.getChildren().clear();

        for(Object[] type : types)
        {
            RadioButton r = new RadioButton(type[0] + " : " +type[1]);
            r.setMinWidth(220);
            r.setOnAction(event -> generateList(data));
            r.setSelected(true);
            typeRadios.add(r);
        }

        List<Object[]> genres = DAO.instance().listGenres();

        Accordion genreWrapper = new Accordion();
        ScrollPane sc = new ScrollPane();
        VBox vb = new VBox();

        for(Object[] genre : genres)
        {
            Button r = new Button(genre[0] + " : " +genre[1]);
            r.setMinWidth(200);
            r.setTextAlignment(TextAlignment.CENTER);
            r.setOnAction(event -> {
                data = DAO.instance().getAllByGenre(genre[0].toString());
                generateList(data);
            });
            genreButtons.add(r);
        }

        vb.getChildren().addAll(genreButtons);
        sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sc.setContent(vb);
        TitledPane tp = new TitledPane("Genres", sc);
        genreWrapper.getPanes().add(tp);

        left.getChildren().addAll(typeRadios);
        left.getChildren().add(new Separator(Orientation.HORIZONTAL));
        left.getChildren().add(genreWrapper);


        storeButtons.clear();
        right.getChildren().clear();

        List<Object[]> stores = DAO.instance().listStores();
        System.out.println(stores);
        for(Object[] store : stores)
        {
            Button r = new Button(store[0] + " : " +store[1]);
            r.setMinWidth(220);
            r.setTextAlignment(TextAlignment.CENTER);
            r.setOnAction(event -> {
                data = DAO.instance().getAllByStore(store[0].toString());
                generateList(data);
            });
            storeButtons.add(r);
        }

        right.getChildren().addAll(storeButtons);

        this.generateList(data);
    }

    @FXML
    public void onSearch() {
        ArrayList<Product> filteredProducts = DAO.instance().searchProduct(searchBar.getText(), searchOpts.getValue());
        this.generateList(filteredProducts);
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
        window.setMaximized(true);
    }
}
