package com.focus.foodtab.service.error;

import com.focus.foodtab.library.enums.ErrorCodes;

public class ErrorMessage
{
    private Integer errorCode;

    private String message;

    private String extraDetails;

    public ErrorMessage()
    {

    }

    public ErrorMessage(ErrorCodes error)
    {
        this.errorCode = error.getErrorCode();
        this.message = error.getErrorMessage();
    }

    public Integer getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getExtraDetails()
    {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails)
    {
        this.extraDetails = extraDetails;
    }
}
