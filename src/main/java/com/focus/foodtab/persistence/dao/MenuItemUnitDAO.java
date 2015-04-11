package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.MenuItemUnitEntity;

public interface MenuItemUnitDAO extends JpaRepository<MenuItemUnitEntity, Integer>
{

}
