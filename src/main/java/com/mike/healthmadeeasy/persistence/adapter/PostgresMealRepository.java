package com.mike.healthmadeeasy.persistence.adapter;

import com.mike.healthmadeeasy.domain.Meal;
import com.mike.healthmadeeasy.domain.MealFoodLink;
import com.mike.healthmadeeasy.persistence.entity.MealEntity;
import com.mike.healthmadeeasy.persistence.entity.MealFoodEntity;
import com.mike.healthmadeeasy.persistence.entity.MealFoodId;
import com.mike.healthmadeeasy.persistence.repository.MealJpaRepository;
import com.mike.healthmadeeasy.repository.MealRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("postgres")
public class PostgresMealRepository implements MealRepository {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final MealJpaRepository mealJpaRepository;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public PostgresMealRepository(MealJpaRepository mealJpaRepository) {
        this.mealJpaRepository = mealJpaRepository;
    }

    //////////////////////////////////
    /// METHODS
    //////////////////////////////////

    /*
    * Made transactional as there are multiple DB calls.
     */
    @Override
    @Transactional
    public Meal save(Meal meal) {

        MealEntity entity = mealJpaRepository.findById(meal.getId())
                .orElseGet(MealEntity::new);

        entity.setId(meal.getId());
        entity.setName(meal.getName());

        entity.getFoods().clear();
        for (MealFoodLink food : meal.getFoods()) {
            MealFoodEntity mealFoodEntity = new MealFoodEntity();

            MealFoodId id = new MealFoodId();
            id.setMealId(meal.getId());
            id.setFoodId(food.getFoodId());
            mealFoodEntity.setId(id);

            mealFoodEntity.setMeal(entity);
            entity.getFoods().add(mealFoodEntity);
        }
        return toDomain(mealJpaRepository.save(entity));
    }

    @Override
    public Optional<Meal> findById(UUID id) {
        return mealJpaRepository
                .findById(id)
                .map(PostgresMealRepository::toDomain);
    }

    @Override
    public List<Meal> findAll() {
        return mealJpaRepository.findAll()
                .stream()
                .map(PostgresMealRepository::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return mealJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(UUID id) {
        mealJpaRepository.deleteById(id);
    }

    //////////////////////////////////
    /// HELPER METHODS
    //////////////////////////////////

    private static Meal toDomain(MealEntity entity) {

        List<MealFoodLink> foodLinkList = entity.getFoods()
                .stream()
                .map(food -> new MealFoodLink(food.getId().getFoodId()))
                .toList();

        return new Meal(entity.getId(), entity.getName(), foodLinkList);
    }

}
