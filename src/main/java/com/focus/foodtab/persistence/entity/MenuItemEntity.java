package com.focus.foodtab.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MENU_ITEM")
public class MenuItemEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CODE")
    private Integer code;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Column(name = "SERVES")
    private Integer serves;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "menuItem", cascade = CascadeType.ALL)
    private MenuItemDetailsEntity menuItemDetails;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menuItem", cascade = CascadeType.ALL)
    private List<MenuItemUnitEntity> menuItemUnits;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Integer getServes()
    {
        return serves;
    }

    public void setServes(Integer serves)
    {
        this.serves = serves;
    }

    public MenuItemDetailsEntity getMenuItemDetails()
    {
        return menuItemDetails;
    }

    public void setMenuItemDetails(MenuItemDetailsEntity menuItemDetails)
    {
        this.menuItemDetails = menuItemDetails;
    }

    public List<MenuItemUnitEntity> getMenuItemUnits()
    {
        return menuItemUnits;
    }

    public void setMenuItemUnits(List<MenuItemUnitEntity> menuItemUnits)
    {
        this.menuItemUnits = menuItemUnits;
    }

}
