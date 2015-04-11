package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.OrderDetailsEntity;

public interface OrderDetailsDAO extends JpaRepository<OrderDetailsEntity, Integer>
{

}
