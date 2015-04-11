package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.MenuEntity;

public interface MenuDAO extends JpaRepository<MenuEntity, Integer>
{

}
