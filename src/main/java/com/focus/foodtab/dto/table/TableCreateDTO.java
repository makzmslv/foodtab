package com.focus.foodtab.dto.table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TableCreateDTO
{
    @NotNull
    @Min(0)
    private Integer tableNo;

    @NotNull
    private Boolean active;

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
