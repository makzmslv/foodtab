package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.CategoryEntity;

public interface CategoryDAO extends JpaRepository<CategoryEntity, Integer>
{

}
