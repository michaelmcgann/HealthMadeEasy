package com.mike.healthmadeeasy.repository;

import com.mike.healthmadeeasy.domain.Food;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodRepository {

    Food save(Food food);

    Optional<Food> findById(UUID id);

    List<Food> findAll();

    boolean existsById(UUID id);

    void deleteById(UUID id);

    List<Food> findAllById(Iterable<UUID> ids);

}
