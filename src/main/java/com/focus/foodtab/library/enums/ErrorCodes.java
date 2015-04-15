package com.focus.foodtab.library.enums;

public enum ErrorCodes
{
    // common
    VALIDATION_ERROR(001, "Invalid Method Argument"),
    NO_FIELDS_UPDATED(002, "No fields were updated"),

    // table
    TABLE_NOT_FOUND(100, "Table does not exist"),
    INVALID_TABLE_NO(101, "Table No cannot be negative"),
    TABLE_ALREADY_EXISTS(102, "Table with Table No already exists"),

    // category
    CATEGORY_NOT_FOUND(201, "Cateogry does not exist"),
    INVALID_CATEGORY_TYPE(202, "Invalid Cateogry Type"),
    INVALID_CATEGORY_SUB_TYPE(203, "Invalid Cateogry Sub Type"),
    CATEGORY_TYPE_SUB_TYPE_MISMATCH(204, "Category Type and Sub Type do not match"),
    CATEGORY_ALREADY_EXISTS(205, "Category with given type and sub type already exists"),
    INVALID_CATEGORY_DISPLAY_RANK(206, "Category with given display order already exists");

    private Integer code;
    private String message;

    private ErrorCodes(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public Integer getErrorCode()
    {
        return code;
    }

    public String getErrorMessage()
    {
        return message;
    }
}
