package com.mike.healthmadeeasy.repository;

import com.mike.healthmadeeasy.domain.Food;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("inmemory")
public class InMemoryFoodRepository implements FoodRepository {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final Map<UUID, Food> store = new ConcurrentHashMap<>();

    //////////////////////////////////
    /// METHODS
    //////////////////////////////////

    @Override
    public Food save(Food food ){
        store.put(food.getId(), food);
        return food;
    }

    @Override
    public Optional<Food> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Food> findAll() {
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
