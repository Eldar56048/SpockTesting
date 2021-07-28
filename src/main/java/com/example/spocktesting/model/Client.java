package com.example.spocktesting.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "discount_id")
    private Discount discount;

    public Client(String name, String surname, String phoneNumber, Discount discount) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.discount = discount;
    }
}

