package com.mike.healthmadeeasy.service;

import com.mike.healthmadeeasy.domain.Food;
import com.mike.healthmadeeasy.domain.Meal;
import com.mike.healthmadeeasy.dto.request.MealCreateRequest;
import com.mike.healthmadeeasy.dto.request.MealFoodRequest;
import com.mike.healthmadeeasy.exception.FoodNotFoundException;
import com.mike.healthmadeeasy.repository.FoodRepository;
import com.mike.healthmadeeasy.repository.MealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealServiceImplTest {

    @Mock
    FoodRepository foodRepository;

    @Mock
    MealRepository mealRepository;

    @InjectMocks
    MealServiceImpl mealService;

    @Test
    void create_createsMeal_and_savesIt_happyPath() {

        UUID foodId1 = UUID.randomUUID();
        UUID foodId2 = UUID.randomUUID();

        MealFoodRequest mealFoodRequest1 = new MealFoodRequest();
        mealFoodRequest1.setFoodId(foodId1);
        MealFoodRequest mealFoodRequest2 = new MealFoodRequest();
        mealFoodRequest2.setFoodId(foodId2);

        MealCreateRequest mealCreateRequest = new MealCreateRequest();
        mealCreateRequest.setName("   Breakfast  ");
        mealCreateRequest.setFoods(List.of(mealFoodRequest1, mealFoodRequest2));

        when(foodRepository.findAllById(List.of(foodId1, foodId2)))
                .thenReturn(List.of(
                        new Food(foodId1, "Apple", new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)),
                        new Food(foodId2, "Banana", new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0))
                ));

        when(mealRepository.save(any(Meal.class))).thenAnswer(returnsFirstArg());

        Meal createdMeal = mealService.create(mealCreateRequest);

        assertNotNull(createdMeal.getId());
        assertEquals("Breakfast", createdMeal.getName());
        assertEquals(2, createdMeal.getFoods().size());
        assertEquals(foodId1, createdMeal.getFoods().get(0).getFoodId());
        assertEquals(foodId2, createdMeal.getFoods().get(1).getFoodId());

        verify(foodRepository).findAllById(List.of(foodId1, foodId2));
        verify(mealRepository).save(any(Meal.class));

        verifyNoMoreInteractions(foodRepository, mealRepository);

    }

    @Test
    void create_throwsMealNotFoundWithInvalidFoodId() {

        UUID foodId1 = UUID.randomUUID();
        UUID foodId2 = UUID.randomUUID();

        MealFoodRequest mealFoodRequest1 = new MealFoodRequest();
        mealFoodRequest1.setFoodId(foodId1);
        MealFoodRequest mealFoodRequest2 = new MealFoodRequest();
        mealFoodRequest2.setFoodId(foodId2);

        MealCreateRequest mealCreateRequest = new MealCreateRequest();
        mealCreateRequest.setName("   Breakfast  ");
        mealCreateRequest.setFoods(List.of(mealFoodRequest1, mealFoodRequest2));


        Food apple = new Food(foodId1, "Apple", new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0));
        when(foodRepository.findAllById(List.of(foodId1, foodId2)))
                .thenReturn(List.of(apple));

        FoodNotFoundException exception = assertThrows(FoodNotFoundException.class,
                () -> mealService.create(mealCreateRequest));

        assertEquals(foodId2.toString(), exception.getMessage());
        verify(foodRepository).findAllById(List.of(foodId1, foodId2));
        verify(mealRepository, never()).save(any(Meal.class));
        verifyNoMoreInteractions(foodRepository, mealRepository);

    }

}
