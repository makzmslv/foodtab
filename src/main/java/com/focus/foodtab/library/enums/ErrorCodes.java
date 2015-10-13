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
    CATEGORY_NOT_FOUND(201, "Category does not exist"),
    INVALID_CATEGORY_TYPE(202, "Invalid Category Type"),
    INVALID_CATEGORY_SUB_TYPE(203, "Invalid Category Sub Type"),
    CATEGORY_TYPE_SUB_TYPE_MISMATCH(204, "Category Type and Sub Type do not match"),
    CATEGORY_ALREADY_EXISTS(205, "Category with given type and sub type already exists"),
    INVALID_CATEGORY_DISPLAY_RANK(206, "Category with given display order already exists"),
    CATEGORY_IN_USE(207, "Category is still being used"),
    DISPLAY_RANK_CANNOT_BE_UPDATED(208, "Cannot update Category Display Rank from this service."),
    CATEGORY_INACTIVE(209, "Category cannot be used as it is inactive"),

    // menuItem
    MENU_ITEM_NOT_FOUND(301, "Menu Item does not exist"),
    MENU_ITEM_ALREADY_EXISTS(302, "Menu Item already exists"),
    DUPLICATE_CODE_FOR_MENU_ITEM(303, "Menu Item with code alreay exists"),
    MENU_ITEM_UNIT_NOT_FOUND(304, "Menu Item Unit does not exist"),
    MENU_ITEM_INACTIVE(305, "Menu Item cannot be used as it is inactive"),
    MENU_ITEM_IN_USE(306, "Menu Item is still being used"),

    // menu
    MENU_ENTRY_NOT_FOUND(401, "Menu Entry not found"),
    MENU_ENTRY_ALREADY_EXISTS(402, "Menu Entry already exists"),

    // order
    ORDER_DOES_NOT_EXIST(501, "Order does not exist"),
    ORDER_ALREADY_IN_PROGRESS_FOR_TABLE(502, "Order already in progress for Table"),
    ORDER_COMPLETED(503, "Cannot update order as already completed"),
    ORDER_DETAIL_DOES_NOT_EXIST(504, "Order Details does not exist"),
    ORDER_ITEM_CANNOT_UPDATED_AS_ALREADY_DELIVERED(505, "Order item cannot be updated as it is already delivered"),
    ORDER_ITEM_CANNOT_UPDATED_AS_ALREADY_CANCELLED(506, "Order item cannot be updated as it is already cancelled"),
    ORDER_ITEM_CANNOT_UPDATED_DUE_TO_TIME_ELAPSED(507, "Order item cannot be updated after 5 minutes"),

    TEST(111111, "Test");

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
