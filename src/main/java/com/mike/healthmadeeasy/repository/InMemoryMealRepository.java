package com.mike.healthmadeeasy.repository;

import com.mike.healthmadeeasy.domain.Meal;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("inmemory")
public class InMemoryMealRepository implements MealRepository {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final Map<UUID, Meal> store = new ConcurrentHashMap<>();

    //////////////////////////////////
    /// METHODS
    //////////////////////////////////

    @Override
    public Meal save(Meal meal) {
        store.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Optional<Meal> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsById(UUID id) {
        return store.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }

}
