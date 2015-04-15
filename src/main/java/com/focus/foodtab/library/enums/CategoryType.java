package com.focus.foodtab.library.enums;

import java.util.ArrayList;
import java.util.List;

public enum CategoryType
{
    FOOD(1),
    DRINKS(2);

    private Integer code;

    private CategoryType(Integer code)
    {
        this.code = code;
    }

    public Integer getCode()
    {
        return code;
    }

    public static List<Integer> getAllTypes()
    {
        List<Integer> codes = new ArrayList<Integer>();
        codes.add(FOOD.getCode());
        codes.add(DRINKS.getCode());
        return codes;
    }
}
