package com.focus.foodtab.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.MenuItemEntity;

public interface MenuItemDAO extends JpaRepository<MenuItemEntity, Integer>
{
    public MenuItemEntity findByName(String name);

    public MenuItemEntity findByCode(Integer code);

    public List<MenuItemEntity> findByActive(Boolean active);
}
