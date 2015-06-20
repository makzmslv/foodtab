package com.focus.foodtab.service.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.mysql.jdbc.Blob;

public class MenuItemDetailsDTO
{
    private Blob blob;

    @NotNull
    @Min(0)
    @Digits(integer = 1, fraction = 1)
    private Integer rating;

    @NotNull
    @Min(1)
    private Integer estimatedTimeInMinutes;

    public Blob getBlob()
    {
        return blob;
    }

    public void setBlob(Blob blob)
    {
        this.blob = blob;
    }

    public Integer getRating()
    {
        return rating;
    }

    public void setRating(Integer rating)
    {
        this.rating = rating;
    }

    public Integer getEstimatedTimeInMinutes()
    {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(Integer estimatedTimeInMinutes)
    {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }
}
