package com.mike.healthmadeeasy.persistence.repository;

import com.mike.healthmadeeasy.persistence.entity.MealEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MealJpaRepository extends JpaRepository<MealEntity, UUID> {

    @Override
    @EntityGraph(attributePaths = "foods")
    @NonNull
    Optional<MealEntity> findById(UUID id);

    @Override
    @EntityGraph(attributePaths = "foods")
    @NonNull
    List<MealEntity> findAll();

}
