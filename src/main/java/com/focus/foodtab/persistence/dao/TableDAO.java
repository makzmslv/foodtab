package com.focus.foodtab.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.focus.foodtab.persistence.entity.TableEntity;

public interface TableDAO extends JpaRepository<TableEntity, Integer>
{
    public TableEntity findByTableNo(Integer tableNo);

    public List<TableEntity> findByActive(Boolean active);
}
