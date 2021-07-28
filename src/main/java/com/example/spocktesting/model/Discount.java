package com.example.spocktesting.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "discounts")
@Data
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    @Column(nullable = false)
    private String discountName;
    @Column(nullable = false)
    private int percentage;

    public Discount(String discountName, int percentage) {
        this.discountName = discountName;
        this.percentage = percentage;
    }
}
