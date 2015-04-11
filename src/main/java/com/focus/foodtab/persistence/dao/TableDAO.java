package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.TableEntity;

public interface TableDAO extends JpaRepository<TableEntity, Integer>
{

}
