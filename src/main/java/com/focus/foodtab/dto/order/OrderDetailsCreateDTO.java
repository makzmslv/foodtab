package com.focus.foodtab.dto.order;

import javax.validation.constraints.NotNull;

public class OrderDetailsCreateDTO
{
    @NotNull
    private Integer quantity;

    @NotNull
    private Integer menuItemId;

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Integer getMenuItemId()
    {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId)
    {
        this.menuItemId = menuItemId;
    }
}
