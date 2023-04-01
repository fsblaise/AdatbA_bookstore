module com.bookstore.bookstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.naming;
    requires ojdbc10;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires jsch;
    requires java.sql;
    requires org.apache.commons.codec;
    requires MaterialFX;

    opens com.bookstore.bookstore;
    opens com.bookstore.bookstore.controllers;
    opens com.bookstore.bookstore.models;
    exports com.bookstore.bookstore;
    exports com.bookstore.bookstore.controllers;
    exports com.bookstore.bookstore.models;
}
