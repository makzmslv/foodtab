package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.MenuItemDetailsEntity;

public interface MenuItemDetailsDAO extends JpaRepository<MenuItemDetailsEntity, Integer>
{

}
