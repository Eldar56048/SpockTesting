package com.example.spocktesting.service;

import java.util.List;

public interface BaseService<T>{
    // your service methods should reflect more of a business intention, than mirror repositories
    // your methods look more suitable for a repository
    List<T> getAll();
    T get(Long id);
    void delete(Long id);
    boolean existsById(Long id);
    T save(T model);
}
