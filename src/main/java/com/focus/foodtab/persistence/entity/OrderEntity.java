package com.focus.foodtab.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER")
public class OrderEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "REF_TABLE")
    private TableEntity table;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private BillEntity bill;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetailsEntity> orderDetails;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public TableEntity getTable()
    {
        return table;
    }

    public void setTable(TableEntity table)
    {
        this.table = table;
    }

}
