package com.bookstore.bookstore.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_STORE_PURCHASE")
@ToString
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private int id;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date dateOfPurchase;
    @Column(length = 6)
    private int price;
    @Column(length = 1)
    private int review;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "BOOK_STORE_PURCHASED_PRODUCTS")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Integer> products;
}
