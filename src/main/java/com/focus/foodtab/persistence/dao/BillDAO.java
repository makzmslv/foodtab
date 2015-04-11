package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.BillEntity;

public interface BillDAO extends JpaRepository<BillEntity, Integer>
{

}
