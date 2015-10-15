package com.focus.foodtab.dto.bill;

import java.math.BigDecimal;
import java.util.Date;

public class BillDTO
{
    private Integer id;

    private Integer tableNo;

    private Integer orderId;

    private BigDecimal billAmount;

    private BigDecimal taxApplied;

    private BigDecimal taxAmount;

    private BigDecimal totalAmount;

    private Date timestamp;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
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

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public BigDecimal getBillAmount()
    {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount)
    {
        this.billAmount = billAmount;
    }

    public BigDecimal getTaxApplied()
    {
        return taxApplied;
    }

    public void setTaxApplied(BigDecimal taxApplied)
    {
        this.taxApplied = taxApplied;
    }

    public BigDecimal getTaxAmount()
    {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount)
    {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }
}
