package com.focus.foodtab.library.enums;

public enum ParameterKeys
{
    // @formatter:off
    TAX("TAX"),
    DISCOUNT("");
    // @formatter:off

    private String key;

    private ParameterKeys(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
}
