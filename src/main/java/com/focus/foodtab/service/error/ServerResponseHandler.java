package com.focus.foodtab.service.error;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.focus.foodtab.library.enums.ErrorCodes;

@ControllerAdvice
public class ServerResponseHandler extends ResponseEntityExceptionHandler
{
    private static final String PACKAGE_ROOT = "com.focus";

    // 400

    @ExceptionHandler(value = { ServerException.class })
    public ResponseEntity<Object> handleBadRequest(final ServerException ex, final WebRequest request)
    {
        return handleExceptionInternal(ex, ex.getErrorMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request)
    {
        ServerResponseEntity bodyOfResponse = new ServerResponseEntity(status, "HTTP message is not readable.");

        if (ex.getCause() instanceof JsonMappingException || ex.getCause() instanceof JsonParseException)
        {
            bodyOfResponse.add("Could not parse JSON");
            JsonProcessingException jsonException = (JsonProcessingException) ex.getCause();
            JsonLocation jsonLocation = jsonException.getLocation();
            if (jsonLocation != null)
            {
                bodyOfResponse.add("on line: " + jsonLocation.getLineNr() + " column: " + jsonLocation.getColumnNr());
                bodyOfResponse.add(jsonException.getOriginalMessage());
            }
        }
        else if (ex.getCause() instanceof IOException)
        {
            bodyOfResponse.add(ex.getClass().getSimpleName());
            bodyOfResponse.add(ex.getMessage());
        }

        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request)
    {
        ErrorMessage errorMessage = new ErrorMessage(ErrorCodes.VALIDATION_ERROR);

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors)
        {
            errorMessage.setExtraDetails(fieldError.getField() + fieldError.getDefaultMessage());
        }

        return handleExceptionInternal(ex, errorMessage, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request)
    {
        ServerResponseEntity bodyOfResponse = new ServerResponseEntity(status, "type mismatch");
        bodyOfResponse.add(ex.getMessage());
        bodyOfResponse.add("failing value: " + ex.getValue().toString());

        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final EntityNotFoundException ex, final WebRequest request)
    {
        ServerResponseEntity bodyOfResponse = new ServerResponseEntity(HttpStatus.NOT_FOUND, "entity not found.");

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // all other exceptions based on RuntimeException
    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request)
    {
        ServerResponseEntity bodyOfResponse = new ServerResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error.");

        getExceptionDetails(ex, bodyOfResponse, ex.getStackTrace());

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private void getExceptionDetails(final RuntimeException ex, ServerResponseEntity bodyOfResponse, StackTraceElement[] stackTrace)
    {
        bodyOfResponse.add(ex.getClass().getSimpleName());
        bodyOfResponse.add(ex.getMessage());
        // cause chain
        Throwable cause = ex.getCause();
        while (cause != null)
        {
            bodyOfResponse.add(cause.getMessage());
            cause = cause.getCause();
        }
        // stacktrace, but only our classes
        bodyOfResponse.add(stackTrace[0].toString());
        for (int i = 1; i < stackTrace.length; i++)
        {
            String stackTraceLine = stackTrace[i].toString();
            if (stackTraceLine.startsWith(PACKAGE_ROOT))
            {
                bodyOfResponse.add(stackTraceLine);
            }
        }
    }
}
