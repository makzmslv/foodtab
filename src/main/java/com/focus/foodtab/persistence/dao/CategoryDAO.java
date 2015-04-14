package com.focus.foodtab.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.CategoryEntity;

public interface CategoryDAO extends JpaRepository<CategoryEntity, Integer>
{
    public CategoryEntity findByNameAndTypeAndSubType(String name, Integer type, Integer subType);

    public CategoryEntity findByDisplayOrder(Integer displayOrder);

    public List<CategoryEntity> findByActive(Boolean active);
}
