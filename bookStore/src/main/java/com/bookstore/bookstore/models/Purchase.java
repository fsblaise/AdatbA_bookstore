package com.bookstore.bookstore.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_STORE_PURCHASE")
@ToString
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @NonNull
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
}
