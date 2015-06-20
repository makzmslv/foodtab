package com.focus.foodtab.service.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class MenuItemUnitDTO
{
    @NotEmpty
    private String unit;

    @NotNull
    @Min(0)
    @Digits(integer = 4, fraction = 0)
    private Integer costOfUnit;

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
}
