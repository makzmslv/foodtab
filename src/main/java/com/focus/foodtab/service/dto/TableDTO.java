package com.focus.foodtab.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TableDTO
{
    private Integer id;

    @NotNull
    @Min(0)
    private Integer tableNo;

    @NotNull
    private Boolean active;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getTableNo()
    {
        return tableNo;
    }

    public void setTableNo(Integer tableNo)
    {
        this.tableNo = tableNo;
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
