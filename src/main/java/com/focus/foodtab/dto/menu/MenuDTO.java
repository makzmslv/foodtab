package com.focus.foodtab.dto.menu;

import com.focus.foodtab.dto.category.CategoryDTO;
import com.focus.foodtab.dto.menuitem.MenuItemDTO;

public class MenuDTO
{
    private Integer id;

    private MenuItemDTO menuItem;

    private CategoryDTO category;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public MenuItemDTO getMenuItem()
    {
        return menuItem;
    }

    public void setMenuItem(MenuItemDTO menuItem)
    {
        this.menuItem = menuItem;
    }

    public CategoryDTO getCategory()
    {
        return category;
    }

    public void setCategory(CategoryDTO category)
    {
        this.category = category;
    }

}
