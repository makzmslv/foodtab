package com.focus.foodtab.service.error;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

public class ServerResponseEntity
{
    ErrorMessage errorMessage;
    ArrayList<String> errorMessages = new ArrayList<String>();

    public ServerResponseEntity()
    {
    }

    public ServerResponseEntity(HttpStatus httpStatus, String message)
    {
        errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(httpStatus.value());
        errorMessage.setMessage(message);
    }

    public ServerResponseEntity(ErrorMessage errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public void add(String longMessage)
    {
        if (longMessage != null)
        {
            for (String message : longMessage.split("\n"))
            {
                errorMessages.add(message);
            }
        }
    }

    // getter / setter

    public ErrorMessage getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public ArrayList<String> getErrorMessages()
    {
        return errorMessages;
    }

    public void setErrorMessages(ArrayList<String> errorMessages)
    {
        this.errorMessages = errorMessages;
    }
}
