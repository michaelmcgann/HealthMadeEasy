package com.mike.healthmadeeasy.repository;

import com.mike.healthmadeeasy.domain.Meal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MealRepository {

    Meal save(Meal meal);

    Optional<Meal> findById(UUID id);

    List<Meal> findAll();

    boolean existsById(UUID id);

    void deleteById(UUID id);

}
