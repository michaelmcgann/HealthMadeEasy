package com.mike.healthmadeeasy.service;

import com.mike.healthmadeeasy.domain.Food;
import com.mike.healthmadeeasy.dto.request.FoodCreateRequest;

import java.util.List;
import java.util.UUID;

public interface FoodService {

    Food create( FoodCreateRequest request );

    Food get( UUID id );

    List<Food> list();

    void delete( UUID id );



}
