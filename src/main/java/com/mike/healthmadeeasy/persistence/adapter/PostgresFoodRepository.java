package com.mike.healthmadeeasy.persistence.adapter;

import com.mike.healthmadeeasy.domain.Food;
import com.mike.healthmadeeasy.persistence.entity.FoodEntity;
import com.mike.healthmadeeasy.persistence.repository.FoodJpaRepository;
import com.mike.healthmadeeasy.repository.FoodRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("postgres")
public class PostgresFoodRepository implements FoodRepository {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final FoodJpaRepository jpa;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public PostgresFoodRepository(FoodJpaRepository jpa) {
        this.jpa = jpa;
    }

    //////////////////////////////////
    /// METHODS
    //////////////////////////////////

    @Override
    public Food save(Food food) {
        FoodEntity saved = jpa.save(toEntity(food));
        return toDomain(saved);
    }

    @Override
    public Optional<Food> findById(UUID id) {
        Optional<FoodEntity> optionalEntity = jpa.findById(id);
        Optional<Food> food = optionalEntity.map(PostgresFoodRepository::toDomain);
        return food;
    }

    @Override
    public List<Food> findAll() {
        return jpa.findAll()
                .stream()
                .map(PostgresFoodRepository::toDomain).toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return jpa.existsById(id);
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<Food> findAllById(Iterable<UUID> ids) {

        return jpa.findAllById(ids)
                .stream()
                .map(PostgresFoodRepository::toDomain)
                .toList();
    }

    //////////////////////////////////
    /// HELPER METHODS
    //////////////////////////////////

    private static FoodEntity toEntity(Food food) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setId(food.getId());
        foodEntity.setName(food.getName());
        foodEntity.setCaloriesPerGram(food.getCaloriesPerGram());
        foodEntity.setProteinPerGram(food.getProteinPerGram());
        foodEntity.setCarbsPerGram(food.getCarbsPerGram());
        foodEntity.setFatPerGram(food.getFatPerGram());

        return foodEntity;
    }

    private static Food toDomain(FoodEntity entity) {
        return new Food(
                entity.getId(),
                entity.getName(),
                entity.getCaloriesPerGram(),
                entity.getProteinPerGram(),
                entity.getCarbsPerGram(),
                entity.getFatPerGram()
        );
    }

}
