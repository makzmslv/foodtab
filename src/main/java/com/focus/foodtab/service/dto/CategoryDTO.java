package com.focus.foodtab.service.dto;

public class CategoryDTO
{
    private Integer id;

    private String name;

    private Integer type;

    private Integer subType;

    private String description;

    private Integer displayRank;

    private Boolean active;

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
