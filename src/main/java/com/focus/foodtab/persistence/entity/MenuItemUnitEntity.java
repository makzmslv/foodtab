package com.focus.foodtab.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MENU_ITEM_UNIT")
public class MenuItemUnitEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "COST_OF_UNIT")
    private Integer costOfUnit;

    @ManyToOne
    @JoinColumn(name = "REF_MENU_ITEM")
    private MenuItemEntity menuItem;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public Integer getCostOfUnit()
    {
        return costOfUnit;
    }

    public void setCostOfUnit(Integer costOfUnit)
    {
        this.costOfUnit = costOfUnit;
    }

    public MenuItemEntity getMenuItem()
    {
        return menuItem;
    }

    public void setMenuItem(MenuItemEntity menuItem)
    {
        this.menuItem = menuItem;
    }

}
