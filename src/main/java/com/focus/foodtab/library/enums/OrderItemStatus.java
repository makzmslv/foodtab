package com.focus.foodtab.library.enums;

import java.util.ArrayList;
import java.util.List;

public enum OrderItemStatus
{
    PLACED(1),
    CANCELLED(2),
    DELIVERED(3);

    private Integer code;

    private OrderItemStatus(Integer code)
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
        codes.add(PLACED.getCode());
        return codes;
    }
}
