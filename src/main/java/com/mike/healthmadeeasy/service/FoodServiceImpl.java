package com.mike.healthmadeeasy.service;

import com.mike.healthmadeeasy.domain.Food;
import com.mike.healthmadeeasy.dto.request.FoodCreateRequest;
import com.mike.healthmadeeasy.exception.FoodNotFoundException;
import com.mike.healthmadeeasy.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;


@Service
public class FoodServiceImpl implements FoodService {

    //////////////////////////////////
    /// FIELDS AND CONSTANTS
    //////////////////////////////////

    private final FoodRepository foodRepository;
    private static final int SCALE = 7;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    public FoodServiceImpl( FoodRepository foodRepository ) {
        this.foodRepository = foodRepository;
    }

    //////////////////////////////////
    /// PUBLIC API
    //////////////////////////////////

    @Override
    public Food create( FoodCreateRequest request ) {

        String name                = request.getName().trim();
        BigDecimal caloriesPerGram = normalisePerGram(request.getCalories(), request.getReferenceGrams());
        BigDecimal proteinPerGram  = normalisePerGram(request.getProtein(), request.getReferenceGrams());
        BigDecimal carbsPerGram    = normalisePerGram(request.getCarbs(), request.getReferenceGrams());
        BigDecimal fatPerGram      = normalisePerGram(request.getFat(), request.getReferenceGrams());

        UUID id = UUID.randomUUID();
        Food food = new Food(id, name, caloriesPerGram, proteinPerGram, carbsPerGram, fatPerGram);

        return foodRepository.save(food);
    }

    @Override
    public Food get(UUID id) {
        return foodRepository.findById(id).orElseThrow(() -> new FoodNotFoundException(id.toString()));
    }

    @Override
    public List<Food> list() {
        return foodRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        Food food = foodRepository.deleteById(id);
        if (food == null) throw new FoodNotFoundException(id.toString());
    }

    //////////////////////////////////
    /// HELPER FUNCTIONS
    //////////////////////////////////

    private static BigDecimal normalisePerGram(BigDecimal macro, int referenceGram) {
        if (referenceGram <= 0) throw new IllegalArgumentException("Reference grams must be > 0");

        BigDecimal grams = BigDecimal.valueOf(referenceGram);

        return macro.divide(grams, SCALE, ROUNDING);
    }

}
