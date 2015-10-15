package com.focus.foodtab.library.enums;

import java.util.ArrayList;
import java.util.List;

public enum OrderStatus
{
    // @formatter:off
    CREATED(1),
    ITEMS_ORDERED(2),
    ORDER_COMPLETED(3),
    BILL_GENERATED(4),
    BILL_PAID(5);
    // @formatter:off
    private Integer code;

    private OrderStatus(Integer code)
    {
        this.code = code;
    }

    public Integer getCode()
    {
        return code;
    }

    public static List<Integer> getOrderInProgresStatuses()
    {
        List<Integer> codes = new ArrayList<Integer>();
        codes.add(CREATED.getCode());
        codes.add(ITEMS_ORDERED.getCode());
        return codes;
    }

    public static List<Integer> getBillGeneratedForOrderStatuses()
    {
        List<Integer> codes = new ArrayList<Integer>();
        codes.add(BILL_GENERATED.getCode());
        codes.add(BILL_PAID.getCode());
        return codes;
    }
}
