package com.bookstore.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_STORE_ONLINE")
public class Online {
    @Id
    private int id;
    private LocalDateTime dateTime; //Change this to zoned dateTime if you want to deal with zoned time
    private String address;
}
