package com.focus.foodtab.dto.order;

import java.util.Date;

import com.focus.foodtab.dto.menuitem.MenuItemDTO;

public class OrderDetailsDTO
{
    private Integer id;

    private Integer orderId;

    private Integer quantity;

    private Integer cost;

    private Integer status;

    private Date timestamp;

    private MenuItemDTO menuItem;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Integer getCost()
    {
        return cost;
    }

    public void setCost(Integer cost)
    {
        this.cost = cost;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }

    public MenuItemDTO getMenuItem()
    {
        return menuItem;
    }

    public void setMenuItem(MenuItemDTO menuItem)
    {
        this.menuItem = menuItem;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
