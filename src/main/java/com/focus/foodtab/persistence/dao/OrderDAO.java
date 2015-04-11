package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.OrderEntity;

public interface OrderDAO extends JpaRepository<OrderEntity, Integer>
{

}
