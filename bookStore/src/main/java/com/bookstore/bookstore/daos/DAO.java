package com.bookstore.bookstore.daos;

import com.bookstore.bookstore.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {

    private static DAO offlineDAO;
    private SessionFactory sessionFactory;

    private DAO() {
        // Create Configuration
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Offline.class);
        configuration.addAnnotatedClass(Online.class);
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Purchase.class);
        configuration.addAnnotatedClass(Stock.class);
        configuration.addAnnotatedClass(Store.class);
        configuration.addAnnotatedClass(User.class);
        // Create Session Factory
        this.sessionFactory = configuration.buildSessionFactory();

        // Initialize Session Object
        Session session = this.sessionFactory.openSession();

        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println("Connected to database");
            }
        });

        session.close();
    }

    public static DAO instance() {
        if (offlineDAO == null) {
            offlineDAO = new DAO();
        }

        return offlineDAO;
    }

    public boolean addData() {
        Session session = this.sessionFactory.openSession();

        Transaction t = session.beginTransaction();
        Offline offline = new Offline(1, "Alma");
        session.save(offline);

        t.commit();

        session.close();

        return true;
    }

    public void closeSession() {
        if (this.sessionFactory.isOpen()) {
            this.sessionFactory.close();
        }
    }
}
