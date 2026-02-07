package com.mike.healthmadeeasy.service;

import com.mike.healthmadeeasy.domain.Meal;
import com.mike.healthmadeeasy.dto.request.MealCreateRequest;

import java.util.List;
import java.util.UUID;

public interface MealService {

    Meal create(MealCreateRequest request);

    Meal get(UUID id);

    List<Meal> list();

    void delete(UUID id);

}
