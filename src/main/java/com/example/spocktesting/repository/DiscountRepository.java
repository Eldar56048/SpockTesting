package com.example.spocktesting.repository;

import com.example.spocktesting.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> findAll();
    boolean existsByDiscountName(String discountName);
    boolean existsByDiscountNameAndIdNotLike(String discountName, Long id);
    boolean existsByPercentage(int percentage);
    boolean existsByPercentageAndIdNotLike(int percentage, Long id);
}
