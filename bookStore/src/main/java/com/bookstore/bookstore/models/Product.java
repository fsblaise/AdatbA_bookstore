package com.bookstore.bookstore.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_STORE_PRODUCT")
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @NonNull
    @Column(length = 6)
    private int id;
    @Column(length = 2)
    private int review;
    @Column(length = 4)
    private String production;
    @Column(length = 20)
    private String type;
    @Column(length = 100)
    private String name;
    @Column(length = 20)
    private String genre;
}
