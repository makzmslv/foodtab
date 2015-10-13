package com.focus.foodtab.dto.menu;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class MenuCreateDTO
{
    @NotNull
    @NotEmpty
    private List<Integer> menuItemIds;

    @NotNull
    private Integer categoryId;

    public List<Integer> getMenuItemIds()
    {
        return menuItemIds;
    }

    public void setMenuItemIds(List<Integer> menuItemIds)
    {
        this.menuItemIds = menuItemIds;
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
