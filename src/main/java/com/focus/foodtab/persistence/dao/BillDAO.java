package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.BillEntity;
import com.focus.foodtab.persistence.entity.OrderEntity;

public interface BillDAO extends JpaRepository<BillEntity, Integer>
{
    public BillEntity findByOrder(OrderEntity orderEntity);
}
