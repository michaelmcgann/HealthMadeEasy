package com.mike.healthmadeeasy.service;

import com.mike.healthmadeeasy.domain.Food;
import com.mike.healthmadeeasy.dto.request.FoodCreateRequest;
import com.mike.healthmadeeasy.exception.FoodNotFoundException;
import com.mike.healthmadeeasy.repository.FoodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoodServiceImplTest {

    @Mock
    FoodRepository foodRepository;

    @InjectMocks
    FoodServiceImpl foodService;

    @Test
    void create_trimsName_and_normalisesMacrosPerGram() {

        FoodCreateRequest request = new FoodCreateRequest();
        request.setName("  apple  ");
        request.setReferenceGrams(100);
        request.setCalories(new BigDecimal("80.00"));
        request.setProtein(new BigDecimal("2"));
        request.setCarbs(new BigDecimal("20"));
        request.setFat(new BigDecimal("1"));

        when(foodRepository.save(any(Food.class))).thenAnswer(returnsFirstArg());

        Food created = foodService.create(request);

        assertNotNull(created.getId());
        assertEquals("apple", created.getName());

        assertEquals(0, created.getCaloriesPerGram().compareTo(new BigDecimal("0.8")));
        assertEquals(0, created.getProteinPerGram().compareTo(new BigDecimal("0.02")));
        assertEquals(0, created.getCarbsPerGram().compareTo(new BigDecimal("0.2")));
        assertEquals(0, created.getFatPerGram().compareTo(new BigDecimal("0.01")));

        verify(foodRepository, times(1)).save(any(Food.class));
        verifyNoMoreInteractions(foodRepository);

    }

    @Test
    void get_throwsFoodNotFoundException_whenMissing() {

        UUID id = UUID.randomUUID();
        when(foodRepository.findById(id)).thenReturn(Optional.empty());

        FoodNotFoundException exception = assertThrows(FoodNotFoundException.class, () -> foodService.get(id));

        assertEquals(id.toString(), exception.getMessage());
        verify(foodRepository, times(1)).findById(id);
        verifyNoMoreInteractions(foodRepository);
    }

    @Test
    void delete_throwsFoodNotFoundException_whenMissing() {

        UUID id = UUID.randomUUID();
        when(foodRepository.findById(id)).thenReturn(Optional.empty());

        FoodNotFoundException exception = assertThrows(FoodNotFoundException.class, () -> foodService.delete(id));

        assertEquals(id.toString(), exception.getMessage());
        verify(foodRepository).findById(id);
        verify(foodRepository, never()).deleteById(id);
        verifyNoMoreInteractions(foodRepository);
    }

    @Test
    void delete_callsDeleteById_whenFoodExists() {
        UUID id = UUID.randomUUID();
        Food food = new Food(id , "test",
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal("1"));

        when(foodRepository.findById(id)).thenReturn(Optional.of(food));

        assertDoesNotThrow(() -> foodService.delete(id));

        verify(foodRepository).findById(id);
        verify(foodRepository).deleteById(id);
        verifyNoMoreInteractions(foodRepository);
    }
}
