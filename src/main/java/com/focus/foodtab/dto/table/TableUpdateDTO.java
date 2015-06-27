package com.focus.foodtab.dto.table;

import javax.validation.constraints.NotNull;

public class TableUpdateDTO
{
    @NotNull
    private Boolean active;

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }
}
