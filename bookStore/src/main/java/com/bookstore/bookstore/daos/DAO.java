package com.bookstore.bookstore.daos;

import com.bookstore.bookstore.models.*;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DAO {
    @Getter
    private static User currentUser;

    private static DAO offlineDAO;
    private final SessionFactory sessionFactory;

    @Getter
    public static HashMap<Integer, Integer> cart = new HashMap<>();

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

    public int addData(Object obj) {
        int id;
        // TODO MAYBE error check
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        id = (Integer) session.save(obj);
        System.out.println(id);

        t.commit();
        session.close();

        return id;
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

        SQLQuery query = session.createNativeQuery(sql).setCacheable(true).setCacheMode(CacheMode.NORMAL);
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

    public void updateUser() {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        ArrayList<User> results = (ArrayList<User>) session.createNativeQuery("SELECT * FROM BOOK_STORE_USERS WHERE email = ? AND password = ?", User.class).setParameter(1, currentUser.getEmail()).setParameter(2, currentUser.getPassword()).list();

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

    public ArrayList<Product> searchProduct(String text, String opt) {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        ArrayList<Product> results = (ArrayList<Product>) session.createNativeQuery(
                opt.equals("Publication Year") ? "SELECT * FROM BOOK_STORE_PRODUCT WHERE production LIKE ?" : "SELECT * FROM BOOK_STORE_PRODUCT WHERE LOWER(name) LIKE ?",
                Product.class).setParameter(1, "%" + text.toLowerCase() + "%").list();

        t.commit();
        session.close();

        return results;
    }

    public List listTypes() {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        List results = session.createNativeQuery("SELECT type, Count(type) FROM BOOK_STORE_PRODUCT GROUP BY type").setCacheable(true).setCacheMode(CacheMode.NORMAL).list();

        t.commit();
        session.close();

        return results;
    }

    public List listGenres() {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        List results = session.createNativeQuery("SELECT genre, Count(genre) FROM BOOK_STORE_PRODUCT GROUP BY genre").setCacheable(true).setCacheMode(CacheMode.NORMAL).list();

        t.commit();
        session.close();

        return results;
    }

    public ArrayList<Product> getAllByGenre(String genre) {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        ArrayList<Product> results = (ArrayList<Product>) session.createNativeQuery("SELECT * FROM BOOK_STORE_PRODUCT WHERE LOWER(genre) LIKE ?", Product.class).setParameter(1, "%" + genre.toLowerCase() + "%").list();

        t.commit();
        session.close();

        return results;
    }

    public List listStores() {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        List results = session.createNativeQuery("Select book_store_store.place FROM book_store_store GROUP BY book_store_store.place").setCacheable(true).setCacheMode(CacheMode.NORMAL).list();

        t.commit();
        session.close();

        return results;
    }

    public ArrayList<Product> getAllByStore(String place) {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        ArrayList<Product> results = (ArrayList<Product>) session.createNativeQuery("SELECT * FROM BOOK_STORE_PRODUCT, BOOK_STORE_STORE, BOOK_STORE_STOCK WHERE book_store_store.id = book_store_stock.store_id and book_store_stock.product_id = book_store_product.id and LOWER(place) LIKE ?", Product.class).setParameter(1, "%" + place.toLowerCase() + "%").list();

        t.commit();
        session.close();

        return results;
    }

    public ArrayList<Store> searchStore(Object[] products) {
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        ArrayList<Store> results = new ArrayList<>();

        if (products != null) {
            for (var object : products) {
                if (results.isEmpty()) {
                    results.addAll(session.createNativeQuery("SELECT store.ID, store.PLACE, store.CAPACITY, store.TYPE, store.COORDS FROM BOOK_STORE_STOCK store2 inner join BOOK_STORE_STORE store ON store.ID = store2.STORE_ID WHERE PRODUCT_ID = ?", Store.class)
                            .setParameter(1, object).list());
                } else {
                    var tmp = (ArrayList<Store>) session.createNativeQuery("SELECT store.ID, store.PLACE, store.CAPACITY, store.TYPE FROM BOOK_STORE_STOCK store2 inner join BOOK_STORE_STORE store ON store.ID = store2.STORE_ID WHERE PRODUCT_ID = ?", Store.class).setParameter(1, object).list();
                    for (var store : tmp) {
                        if (!results.contains(store)) {
                            results.remove(store);
                        }
                    }
                }
            }
        }

        t.commit();
        session.close();

        return results;
    }

    public Purchase addPurchaseToViewAndGet(Purchase purchase) throws InterruptedException {
        var items = purchase.getProducts();
        Session session = this.sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        session.createNativeQuery("INSERT INTO BOOK_STORE_VW_PURCHASE(dateofpurchase, price, review, user_id) VALUES (?,?,?,?)")
                .setDate(1, purchase.getDateOfPurchase())
                .setParameter(2, purchase.getPrice())
                .setParameter(3, purchase.getReview())
                .setParameter(4, purchase.getUser().getId()).setHibernateFlushMode(FlushMode.ALWAYS).executeUpdate();

        t.commit();
        session.close();

        TimeUnit.MILLISECONDS.sleep(1000); //TODO: Maybe change this i don't know pleas god send us help

        session = this.sessionFactory.openSession();
        t = session.beginTransaction();

        ArrayList<Purchase> purchases = (ArrayList<Purchase>) session.createNativeQuery("SELECT * FROM BOOK_STORE_PURCHASE WHERE DATEOFPURCHASE = ?", Purchase.class).setDate(1, purchase.getDateOfPurchase()).list();
        if (purchases.size() == 0) {
            purchase = null;
        } else {
            purchase = purchases.get(0);
        }

        t.commit();
        session.close();

        session = this.sessionFactory.openSession();

        for (var item : items) {
            t = session.beginTransaction();

            if (purchase != null) {
                session.createNativeQuery("INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(PURCHASE_ID, PRODUCTS) VALUES (?,?)")
                        .setParameter(1, purchase.getId())
                        .setParameter(2, item)
                        .setHibernateFlushMode(FlushMode.ALWAYS).executeUpdate();
            }

            t.commit();
        }

        session.close();

        return purchase;
    }
}
