package com.focus.foodtab.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.OrderDetailsEntity;
import com.focus.foodtab.persistence.entity.OrderEntity;

public interface OrderDetailsDAO extends JpaRepository<OrderDetailsEntity, Integer>
{
    List<OrderDetailsEntity> findByOrder(OrderEntity orderEntity);

    List<OrderDetailsEntity> findByOrderAndStatusNotIn(OrderEntity orderEntity, List<Integer> orderStatus);
}
