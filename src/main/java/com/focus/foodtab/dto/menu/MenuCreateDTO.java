package com.focus.foodtab.dto.menu;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class MenuCreateDTO
{
    @NotNull
    @NotEmpty
    private Integer menuItemId;

    @NotNull
    private Integer categoryId;

    public Integer getMenuItemId()
    {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId)
    {
        this.menuItemId = menuItemId;
    }

    public Integer getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }
}
