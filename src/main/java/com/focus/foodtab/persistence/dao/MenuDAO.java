package com.focus.foodtab.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.focus.foodtab.persistence.entity.CategoryEntity;
import com.focus.foodtab.persistence.entity.MenuEntity;
import com.focus.foodtab.persistence.entity.MenuItemEntity;

public interface MenuDAO extends JpaRepository<MenuEntity, Integer>
{
    public List<MenuEntity> findByMenuItem(MenuItemEntity menuItem);

    public List<MenuEntity> findByCategory(CategoryEntity category);

    @Query(value = "SELECT menuEntry.menuItem.id FROM MenuEntity menuEntry WHERE menuEntry.id IS NOT NULL")
    public List<Integer> getMenuItemIds();

    public MenuEntity findByMenuItemAndCategory(MenuItemEntity menuItem, CategoryEntity category);
}
