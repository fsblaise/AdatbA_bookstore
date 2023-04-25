package com.bookstore.bookstore.models;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_STORE_USERS")
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private int id;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(length = 5)
    private int purchasedProducts;
    @Column(length = 30)
    private String email;
    @Column(length = 50)
    private String name;
    @Column(length = 64)
    private String password;
    private Boolean isRegular;
}
