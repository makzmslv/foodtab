package com.focus.foodtab.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ORDER_DETAILS")
public class OrderDetailsEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "COST_OF_ITEM")
    private Integer costOfItem;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "KOT")
    private Date timestamp;

    @Column(name = "STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "REF_ORDER")
    private OrderEntity orderEntity;

    @OneToOne
    @JoinColumn(name = "REF_MENU_ITEM")
    private MenuItemEntity menuItem;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Integer getCostOfItem()
    {
        return costOfItem;
    }

    public void setCostOfItem(Integer costOfItem)
    {
        this.costOfItem = costOfItem;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public OrderEntity getOrder()
    {
        return orderEntity;
    }

    public void setOrder(OrderEntity order)
    {
        this.orderEntity = order;
    }

    public MenuItemEntity getMenuItem()
    {
        return menuItem;
    }

    public void setMenuItem(MenuItemEntity menuItem)
    {
        this.menuItem = menuItem;
    }

}
