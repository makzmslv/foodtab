package com.focus.foodtab.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TABLE")
public class TableEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "TABLE_NO")
    private BigDecimal tableNo;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private List<BillEntity> bills;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public BigDecimal getTableNo()
    {
        return tableNo;
    }

    public void setTableNo(BigDecimal tableNo)
    {
        this.tableNo = tableNo;
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
