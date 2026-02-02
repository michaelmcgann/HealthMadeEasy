package com.mike.healthmadeeasy.repository;

import com.mike.healthmadeeasy.domain.Food;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryFoodRepository implements FoodRepository {

    private final Map<UUID, Food> store = new ConcurrentHashMap<>();

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
    public Food deleteById(UUID id) {
        return store.remove(id);
    }

}
