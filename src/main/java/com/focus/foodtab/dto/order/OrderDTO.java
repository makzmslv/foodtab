package com.focus.foodtab.dto.order;

public class OrderDTO
{
    private Integer id;

    private Integer status;

    private Integer tableNo;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
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

    public Integer getTableNo()
    {
        return tableNo;
    }

    public void setTableNo(Integer tableNo)
    {
        this.tableNo = tableNo;
    }
}
