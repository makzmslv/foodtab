package com.focus.foodtab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.ParameterEntity;

public interface ParameterDAO extends JpaRepository<ParameterEntity, Integer>
{
    public ParameterEntity findByKey(String key);
}
