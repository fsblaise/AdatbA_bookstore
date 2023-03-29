package com.bookstore.bookstore.models;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_STORE_STORE")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @NonNull
    @Column(length = 5)
    private int id;
    @Column(length = 7)
    private String type;
    @Column(unique = true, length = 100)
    private String place;
    @Column(length = 6)
    private int capacity;
}
