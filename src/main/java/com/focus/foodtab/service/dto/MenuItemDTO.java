package com.focus.foodtab.service.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class MenuItemDTO
{
    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    @Min(0)
    private Integer code;

    @NotNull
    private Boolean active;

    @NotNull
    @Min(0)
    @Digits(integer = 4, fraction = 0)
    private Integer price;

    @NotNull
    @Min(1)
    private Integer serves;

    @NotNull
    @Valid
    private MenuItemDetailsDTO menuItemDetails;

    @NotNull
    @Valid
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
