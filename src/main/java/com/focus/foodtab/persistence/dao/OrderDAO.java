package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.OrderEntity;
import com.focus.foodtab.persistence.entity.TableEntity;

public interface OrderDAO extends JpaRepository<OrderEntity, Integer>
{
    public OrderEntity findByTableAndStatusNot(TableEntity tableEntity, Integer status);
}
