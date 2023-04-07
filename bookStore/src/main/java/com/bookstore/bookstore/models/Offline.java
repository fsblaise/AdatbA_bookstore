package com.bookstore.bookstore.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_STORE_OFFLINE")
@ToString
public class Offline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private int id;

    @OneToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private Purchase purchase;

    @OneToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;
}
