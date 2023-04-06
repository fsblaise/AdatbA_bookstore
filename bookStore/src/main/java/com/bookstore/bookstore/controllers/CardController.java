package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class CardController {
    @FXML
    public ImageView image;
    @FXML
    public Label name;
    public Node node;

    public CardController() throws IOException {
        FXMLLoader card = new FXMLLoader(MainApplication.class.getResource("card.fxml"));
        node = card.load();
    }
}
