package com.bookstore.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Online {
    private int id;
    private LocalDateTime dateTime; //Change this to zoned dateTime if you want to deal with zoned time
    private String address;
}
