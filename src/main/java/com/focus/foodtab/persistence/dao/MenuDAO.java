package com.focus.foodtab.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.MenuEntity;
import com.focus.foodtab.persistence.entity.MenuItemEntity;

public interface MenuDAO extends JpaRepository<MenuEntity, Integer>
{
    public List<MenuEntity> findByMenuItem(MenuItemEntity menuItem);
}
