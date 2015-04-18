package com.focus.foodtab.service.dto;

import java.util.List;

public class MenuItemDTO
{
    private String name;

    private String description;

    private Integer code;

    private Boolean active;

    private Integer price;

    private Integer serves;

    private MenuItemDetailsDTO details;

    private List<MenuItemUnitDTO> units;

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

    public MenuItemDetailsDTO getDetails()
    {
        return details;
    }

    public void setDetails(MenuItemDetailsDTO details)
    {
        this.details = details;
    }

    public List<MenuItemUnitDTO> getUnits()
    {
        return units;
    }

    public void setUnits(List<MenuItemUnitDTO> units)
    {
        this.units = units;
    }
}
