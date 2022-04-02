package com.example.foodieapp.iteam;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByNameIgnoreCase(String replaceAll);
}