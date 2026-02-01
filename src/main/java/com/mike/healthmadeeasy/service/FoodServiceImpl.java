package com.mike.healthmadeeasy.service;

import com.mike.healthmadeeasy.domain.Food;
import com.mike.healthmadeeasy.dto.request.FoodCreateRequest;
import com.mike.healthmadeeasy.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FoodServiceImpl implements FoodService {

    //////////////////////////////////
    /// FIELDS (dependencies)
    //////////////////////////////////

//    private final FoodRepository foodRepository;

    public FoodServiceImpl( /*FoodRepository foodRepository*/ ) {
//        this.foodRepository = foodRepository;
    }

    //////////////////////////////////
    /// PUBLIC API
    //////////////////////////////////

    @Override
    public Food create( FoodCreateRequest request ) {

        // 1) Extract & sanitize inputs
        // - String name = req.getName().trim();
        // - int referenceGrams = req.getReferenceGrams();
        // - BigDecimal calories = req.getCalories();
        // - BigDecimal protein  = req.getProtein();
        // - BigDecimal carbs    = req.getCarbs();
        // - BigDecimal fat      = req.getFat();
        String name         = request.getName().trim();
        int referenceGrams  = request.getReferenceGrams();
        BigDecimal calories = request.getCalories();
        BigDecimal protein  = request.getProtein();
        BigDecimal carbs    = request.getCarbs();
        BigDecimal fat      = request.getFat();


        // 3) Convert totals-per-referenceGrams into per-gram macros
        // - BigDecimal caloriesPerGram = normalizePerGram(calories, referenceGrams);
        // - BigDecimal proteinPerGram  = normalizePerGram(protein,  referenceGrams);
        // - BigDecimal carbsPerGram    = normalizePerGram(carbs,    referenceGrams);
        // - BigDecimal fatPerGram      = normalizePerGram(fat,      referenceGrams);

        // 4) Create domain object (immutable)
        // - UUID id = UUID.randomUUID();
        // - Food food = new Food(id, name, caloriesPerGram, proteinPerGram, carbsPerGram, fatPerGram);

        // 5) Persist (later)
        // - foodRepository.save(food);

        // 6) Return domain
        // - return food;


    }

}
