package com.focus.foodtab.common.util;

import java.util.ArrayList;
import java.util.List;

public enum CategorySubType
{
    VEG(1),
    NON_VEG(2),
    ALCOHOLIC(3),
    NON_ALCOHOLIC(4);

    private Integer code;

    private CategorySubType(Integer code)
    {
        this.code = code;
    }

    public Integer getCode()
    {
        return code;
    }

    public static List<Integer> getAllSubTypes()
    {
        List<Integer> codes = new ArrayList<Integer>();
        codes.add(VEG.getCode());
        codes.add(NON_VEG.getCode());
        codes.add(ALCOHOLIC.getCode());
        codes.add(NON_ALCOHOLIC.getCode());
        return codes;
    }

    public static List<Integer> getFoodSubTypes()
    {
        List<Integer> codes = new ArrayList<Integer>();
        codes.add(VEG.getCode());
        codes.add(NON_VEG.getCode());
        return codes;
    }

    public static List<Integer> getDrinkSubTypes()
    {
        List<Integer> codes = new ArrayList<Integer>();
        codes.add(ALCOHOLIC.getCode());
        codes.add(NON_ALCOHOLIC.getCode());
        return codes;
    }
}
