package com.focus.foodtab.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class CategoryEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "SUB_TYPE")
    private Integer subType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DISPLAY_RANK")
    private Integer displayRank;

    @Column(name = "ACTIVE")
    private Boolean active;

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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((active == null) ? 0 : active.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((subType == null) ? 0 : subType.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CategoryEntity other = (CategoryEntity) obj;
        if (active == null)
        {
            if (other.active != null)
                return false;
        }
        else if (!active.equals(other.active))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (subType == null)
        {
            if (other.subType != null)
                return false;
        }
        else if (!subType.equals(other.subType))
            return false;
        if (type == null)
        {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        return true;
    }

}
