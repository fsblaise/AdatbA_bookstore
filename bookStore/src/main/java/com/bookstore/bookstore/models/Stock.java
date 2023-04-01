package com.bookstore.bookstore.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_STORE_STOCK")
@ToString
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 5)
    private int name;
    @Column(length = 4)
    private int capacity;
    @Column(length = 3)
    private int sum;
    private int isLow;
}
