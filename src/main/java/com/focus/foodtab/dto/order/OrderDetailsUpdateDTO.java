package com.focus.foodtab.dto.order;

import javax.validation.constraints.NotNull;

public class OrderDetailsUpdateDTO
{
    @NotNull
    private Integer orderDetailsId;

    @NotNull
    private Integer status;

    public Integer getOrderDetailsId()
    {
        return orderDetailsId;
    }

    public void setOrderDetailsId(Integer orderDetailsId)
    {
        this.orderDetailsId = orderDetailsId;
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
