package com.bookstore.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private Date birthDate;
    private int purchasedProducts;
    private String email;
    private String name;
    private boolean isRegular;
}
