package com.focus.foodtab.dto.menuitem;

import java.util.List;

public class MenuItemDTO
{
    private Integer id;

    private String name;

    private String description;

    private Integer code;

    private Boolean active;

    private Integer price;

    private Integer serves;

    private MenuItemDetailsDTO menuItemDetails;

    private List<MenuItemUnitDTO> menuItemUnits;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
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

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public Integer getServes()
    {
        return serves;
    }

    public void setServes(Integer serves)
    {
        this.serves = serves;
    }

    public MenuItemDetailsDTO getMenuItemDetails()
    {
        return menuItemDetails;
    }

    public void setMenuItemDetails(MenuItemDetailsDTO menuItemDetails)
    {
        this.menuItemDetails = menuItemDetails;
    }

    public List<MenuItemUnitDTO> getMenuItemUnits()
    {
        return menuItemUnits;
    }

    public void setMenuItemUnits(List<MenuItemUnitDTO> menuItemUnits)
    {
        this.menuItemUnits = menuItemUnits;
    }
}
