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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(length = 5)
    @NonNull
    private int name;
    @Column(length = 4)
    private int capacity;
    @Column(length = 3)
    private int sum;
    private int isLow;
}
