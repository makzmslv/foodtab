package com.focus.foodtab.dto.category;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CategoryCreateDTO
{
    @NotEmpty
    private String name;

    @NotNull
    private Integer type;

    @NotNull
    private Integer subType;

    private String description;

    @NotNull
    @Min(0)
    private Integer displayRank;

    @NotNull
    private Boolean active;

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

    public Integer getDisplayRank()
    {
        return displayRank;
    }

    public void setDisplayRank(Integer displayOrder)
    {
        this.displayRank = displayOrder;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }
}
