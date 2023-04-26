package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import com.bookstore.bookstore.daos.DAO;
import com.bookstore.bookstore.models.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class CheckOutController {
    public HBox toolbar;
    @FXML
    private BorderPane content;
    @FXML
    private Button doneOnline;
    private List<Node> items;
    ArrayList<Product> cartArray = new ArrayList<>();
    private boolean isAdmin;

    @FXML
    public void initialize() {
        onBasketClicked();
    }

    public void onDoneOnline() {
        TextInputDialog dialog = new TextInputDialog("Address");
        dialog.setTitle("Address for the order");
        dialog.setHeaderText("Write address");
        dialog.setContentText("Address:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(s -> {
            Date date = new Date();
            Purchase purchase = new Purchase();
            purchase.setDateOfPurchase(date);
            purchase.setUser(DAO.getCurrentUser());
            purchase.setPrice(1000);

            ArrayList<Integer> products = new ArrayList<>();

            var cart = DAO.getCart();

            for (var key : cart.keySet()) {
                Product product = DAO.instance().getDataByID(Product.class, key);
                for (int i = 0; i < cart.get(key); i++) {
                    products.add(product.getId());
                }
            }

            purchase.setProducts(products);

            TextInputDialog dialog2 = new TextInputDialog("5");
            dialog2.setTitle("review");
            dialog2.setHeaderText("review");
            dialog2.setContentText("review");

            Optional<String> result2 = dialog2.showAndWait();

            if (result2.isPresent()) {
                try {
                    purchase.setReview(Integer.parseInt(result2.get()));
                } catch (NumberFormatException e) {
                    purchase.setReview(0);
                }
            } else {
                purchase.setReview(0);
            }

            try {
                purchase = DAO.instance().addPurchaseToViewAndGet(purchase);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Online online = new Online();

            online.setAddress(s);
            online.setDateTime(date);
            online.setPurchase(purchase);

            DAO.instance().addData(online);
            DAO.cart.clear();

            Parent root;

            try {
                root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("main-view.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage window = (Stage) doneOnline.getScene().getWindow();
            window.setScene(new Scene(root, 700, 500));
            window.setMaximized(true);
        });
    }

    public void onDoneOffline() {
        var stores = DAO.instance().searchStore(DAO.cart.keySet().toArray()).toArray(new Store[0]);
        if (stores.length == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("The book can't bought in any of our stores");
            alert.setContentText("Pleas try again later");
            alert.showAndWait();
            return;
        }
        ChoiceDialog<Store> d = new ChoiceDialog<>(stores[0], stores);

        Optional<Store> store = d.showAndWait();

        store.ifPresent(s -> {
            Date date = new Date();
            Purchase purchase = new Purchase();
            purchase.setDateOfPurchase(date);
            purchase.setUser(DAO.getCurrentUser());
            purchase.setPrice(1000);

            ArrayList<Integer> products = new ArrayList<>();

            var cart = DAO.getCart();

            for (var key : cart.keySet()) {
                Product product = DAO.instance().getDataByID(Product.class, key);
                for (int i = 0; i < cart.get(key); i++) {
                    products.add(product.getId());
                }
            }

            purchase.setProducts(products);

            TextInputDialog dialog2 = new TextInputDialog("5");
            dialog2.setTitle("review");
            dialog2.setHeaderText("review");
            dialog2.setContentText("review");

            Optional<String> result2 = dialog2.showAndWait();

            if (result2.isPresent()) {
                try {
                    purchase.setReview(Integer.parseInt(result2.get()));
                } catch (NumberFormatException e) {
                    purchase.setReview(5);
                }
            } else {
                purchase.setReview(5);
            }

            try {
                purchase = DAO.instance().addPurchaseToViewAndGet(purchase);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Offline offline = new Offline();

            offline.setStore(s);
            offline.setPurchase(purchase);

            DAO.instance().addData(offline);
            DAO.cart.clear();

            Parent root;

            try {
                root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("main-view.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage window = (Stage) doneOnline.getScene().getWindow();
            window.setScene(new Scene(root, 700, 500));
            window.setMaximized(true);
        });
    }

    public void onCancel() {
        Parent root;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("main-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage window = (Stage) doneOnline.getScene().getWindow();
        window.setScene(new Scene(root, 700, 500));
        window.setMaximized(true);
    }

    public void generateTable(ArrayList<Product> data) {
        if (items != null) {
            content.getChildren().removeAll(items);
        }

        data.sort(Comparator.comparingInt(Product::getId));

        items = new ArrayList<>();
        TableView<Product> dataTable = new TableView<>();
        dataTable.setLayoutY(150);
        dataTable.setPrefWidth(content.getPrefWidth());
        dataTable.setPrefHeight(200);

        dataTable.getColumns().clear();
        dataTable.getItems().clear();
        List<Field> properties = Arrays.asList(data.get(0).getClass().getDeclaredFields());
        TableColumn[] columns = new TableColumn[properties.size()];

        for (int i = 0; i < columns.length; i++) {
            columns[i] = new TableColumn<Product, String>(properties.get(i).getName());
            columns[i].setCellValueFactory(new PropertyValueFactory<>(properties.get(i).getName()));
            dataTable.getColumns().add(columns[i]);
        }

        for (var item : data) {
            dataTable.getItems().add(item);
        }

        TableColumn<Product, Void> moreButton = new TableColumn<>("more");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> moreCellFactory = new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("more");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product data1 = getTableView().getItems().get(getIndex());
                            cartArray.add(data1);

                            DAO.cart.put(data1.getId(), DAO.cart.get(data1.getId()) + 1);

                            generateTable(cartArray);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        moreButton.setCellFactory(moreCellFactory);

        dataTable.getColumns().add(moreButton);

        TableColumn<Product, Void> deleteButton = new TableColumn<>("delete");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> deleteCellFactory = new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product data1 = getTableView().getItems().get(getIndex());
                            cartArray.remove(data1);
                            DAO.cart.put(data1.getId(), DAO.cart.get(data1.getId()) - 1);

                            if (DAO.cart.get(data1.getId()) <= 0) {
                                DAO.cart.remove(data1.getId());
                            }

                            if (cartArray.size() == 0) {
                                Parent root;

                                try {
                                    root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("main-view.fxml")));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                Stage window = (Stage) doneOnline.getScene().getWindow();
                                window.setScene(new Scene(root, 700, 500));
                                window.setMaximized(true);
                                return;
                            }

                            generateTable(cartArray);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        deleteButton.setCellFactory(deleteCellFactory);
        dataTable.getColumns().add(deleteButton);
        items.add(dataTable);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(items);
        content.setCenter(stackPane);
    }

    public void onBasketClicked() {
        isAdmin = DAO.getCurrentUser().getEmail().equals("admin");
        if (isAdmin) {
            Button admin = new Button("Admin");
            admin.setOnAction(actionEvent -> {
                this.onAdminClicked();
            });
            admin.getStyleClass().addAll("nav-button", "raised");
            admin.setMinWidth(70);
            toolbar.getChildren().add(admin);
        }

        var cart = DAO.getCart();

        for (var key : cart.keySet()) {
            Product product = DAO.instance().getDataByID(Product.class, key);
            for (int i = 0; i < cart.get(key); i++) {
                this.cartArray.add(product);
            }
        }

        this.generateTable(this.cartArray);
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

    public void onProductsButtonClick(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("main-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainApplication.getMainStage().getScene().setRoot(root);
    }

    public void onLogOutButtonClick(ActionEvent actionEvent) {
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
