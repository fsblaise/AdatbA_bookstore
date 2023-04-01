package com.bookstore.bookstore.daos;

import com.bookstore.bookstore.models.*;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;

public class DAO {
    @Getter
    private static User currentUser;

    private static DAO offlineDAO;
    private final SessionFactory sessionFactory;

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

        session.doWork(connection -> System.out.println("Connected to database"));

        session.close();
    }

    public static DAO instance() {
        if (offlineDAO == null) {
            offlineDAO = new DAO();
        }

        return offlineDAO;
    }

    public void addData(Object obj) {
        // TODO MAYBE error check
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        session.save(obj);

        t.commit();
        session.close();
    }

    public <T> T getDataByID(Class<T> entityClass, int id) {
        T obj;
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        obj = session.get(entityClass, id);

        t.commit();
        session.close();

        return obj;
    }

    public void updateData(Object obj) {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        session.update(obj);

        t.commit();
        session.close();
    }

    public void deleteData(Object obj) {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        session.delete(obj);

        t.commit();
        session.close();
    }

    // TODO: private
    public <T> ArrayList<T> runCustomQuery(Class<T> entityClass, String sql) {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        SQLQuery query = session.createNativeQuery(sql);
        query.addEntity(entityClass);
        ArrayList<T> results = (ArrayList<T>) query.list();

        t.commit();
        session.close();

        return results;
    }

    public void loginUser(String email, String password) {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        ArrayList<User> results = (ArrayList<User>) session.createNativeQuery("SELECT * FROM BOOK_STORE_USERS WHERE email = ? AND password = ?", User.class).setParameter(1, email).setParameter(2, DigestUtils.sha256Hex(password)).list();

        if (results.size() != 0) {
            currentUser = results.get(0);
        }

        t.commit();
        session.close();
    }

    public void closeSession() {
        if (this.sessionFactory.isOpen()) {
            this.sessionFactory.close();
        }
    }
}
