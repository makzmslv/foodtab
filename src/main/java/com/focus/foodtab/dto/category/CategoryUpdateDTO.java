package com.focus.foodtab.dto.category;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CategoryUpdateDTO
{
    @NotEmpty
    private String name;

    @NotNull
    private Integer type;

    @NotNull
    private Integer subType;

    private String description;

    @NotNull
    private boolean active;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getSubType()
    {
        return subType;
    }

    public void setSubType(Integer subType)
    {
        this.subType = subType;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean getActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
