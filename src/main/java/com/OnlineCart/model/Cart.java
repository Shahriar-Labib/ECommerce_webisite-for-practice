package com.OnlineCart.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private UserDatas user;

    @ManyToOne
    private Product product;

    private  Integer quantity;

    @Transient
    private Double totalPrice;
}
