package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.MenuItemEntity;

public interface MenuItemDAO extends JpaRepository<MenuItemEntity, Integer>
{

}
