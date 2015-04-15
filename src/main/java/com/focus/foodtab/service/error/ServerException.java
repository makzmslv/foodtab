package com.focus.foodtab.service.error;

public class ServerException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    protected ErrorMessage errorMessage = null;

    public ServerException(ErrorMessage errorMessage)
    {
        super();
        this.errorMessage = errorMessage;
    }

    public ServerException(ErrorMessage errorMessage, Throwable cause)
    {
        super(cause);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage()
    {
        return errorMessage;
    }
}
