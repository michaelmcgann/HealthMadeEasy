package com.mike.healthmadeeasy.persistence.repository;

import com.mike.healthmadeeasy.persistence.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodJpaRepository extends JpaRepository<FoodEntity, UUID> {
}
