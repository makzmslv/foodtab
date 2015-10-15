package com.focus.foodtab.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TABLES")
public class TableEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "TABLE_NO")
    private Integer tableNo;

    @Column(name = "ACTIVE")
    private Boolean active;

    @OneToMany(mappedBy = "tableEntity", cascade = CascadeType.ALL)
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "tableEntity", cascade = CascadeType.ALL)
    private List<BillEntity> bills;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public List<BillEntity> getBills()
    {
        return bills;
    }

    public void setBills(List<BillEntity> bills)
    {
        this.bills = bills;
    }

    public List<OrderEntity> getOrders()
    {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders)
    {
        this.orders = orders;
    }

}
