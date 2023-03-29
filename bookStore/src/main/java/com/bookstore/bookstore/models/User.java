package com.bookstore.bookstore.models;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_STORE_USER")
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(length = 10)
    @NonNull
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
    private boolean isRegular;
}
