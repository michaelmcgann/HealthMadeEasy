package com.mike.healthmadeeasy.service;

import com.mike.healthmadeeasy.domain.Food;
import com.mike.healthmadeeasy.domain.Meal;
import com.mike.healthmadeeasy.domain.MealFoodLink;
import com.mike.healthmadeeasy.dto.request.MealCreateRequest;
import com.mike.healthmadeeasy.dto.request.MealFoodRequest;
import com.mike.healthmadeeasy.exception.FoodNotFoundException;
import com.mike.healthmadeeasy.exception.MealNotFoundException;
import com.mike.healthmadeeasy.repository.FoodRepository;
import com.mike.healthmadeeasy.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    //////////////////////////////////
    /// FIELDS
    //////////////////////////////////

    private final MealRepository mealRepository;
    private final FoodRepository foodRepository;

    //////////////////////////////////
    /// CONSTRUCTORS
    //////////////////////////////////

    public MealServiceImpl(MealRepository mealRepository, FoodRepository foodRepository) {
        this.mealRepository = mealRepository;
        this.foodRepository = foodRepository;
    }

    //////////////////////////////////
    /// PUBLIC API
    //////////////////////////////////

    @Override
    public Meal create(MealCreateRequest request) {

        String name = request.getName().trim();

        // Exacts Ids and removes duplicates
        List<UUID> ids = request.getFoods()
                .stream()
                .map(MealFoodRequest::getFoodId)
                .distinct()
                .toList();

        assertFoodsExists(ids);

        UUID mealId = UUID.randomUUID();

        List<MealFoodLink> foodLinks = ids.stream()
                .map(MealFoodLink::new)
                .toList();

        Meal meal = new Meal(mealId, name, foodLinks);

        return mealRepository.save(meal);
    }

    @Override
    public Meal get(UUID id) {
        return mealRepository.findById(id).orElseThrow(() -> new MealNotFoundException(id.toString()));
    }

    @Override
    public List<Meal> list() {
        return mealRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        if (!mealRepository.existsById(id)) throw new MealNotFoundException(id.toString());
        mealRepository.deleteById(id);
    }

    //////////////////////////////////
    /// HELPER METHODS
    //////////////////////////////////

    private void  assertFoodsExists(List<UUID> ids) {

        List<Food> found = foodRepository.findAllById(ids);

        Set<UUID> foundIds = found
                .stream()
                .map(Food::getId)
                .collect(Collectors.toSet());

        for (UUID requestedId : ids) {
            if (!(foundIds.contains(requestedId))) {
                throw new FoodNotFoundException(requestedId.toString());
            }
        }
    }

}
