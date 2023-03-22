module com.bookstore.bookstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.bookstore.bookstore to javafx.fxml;
    exports com.bookstore.bookstore;
    exports com.bookstore.bookstore.controllers;
    opens com.bookstore.bookstore.controllers to javafx.fxml;
}
