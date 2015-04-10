package com.focus.foodtab.persistence.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MENU")
public class MenuEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "REF_MENU_ITEM")
    private MenuItemEntity menuItem;

    @OneToOne
    @JoinColumn(name = "REF_MENU_ITEM")
    private CategoryEntity category;

    public MenuItemEntity getMenuItem()
    {
        return menuItem;
    }

    public void setMenuItem(MenuItemEntity menuItem)
    {
        this.menuItem = menuItem;
    }

    public CategoryEntity getCategory()
    {
        return category;
    }

    public void setCategory(CategoryEntity category)
    {
        this.category = category;
    }

}
