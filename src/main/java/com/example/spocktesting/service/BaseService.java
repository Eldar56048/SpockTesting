package com.example.spocktesting.service;

import java.util.List;

public interface BaseService<T>{
    List<T> getAll();
    T get(Long id);
    void delete(Long id);
}
