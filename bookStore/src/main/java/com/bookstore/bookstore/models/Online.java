package com.bookstore.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_STORE_ONLINE")
public class Online {
    @Id
    @Column(length = 10)
    private int id;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date dateTime;
    @Column(length = 20)
    private String address;

    @OneToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private Purchase purchase;
}
