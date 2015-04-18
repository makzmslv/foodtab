package com.focus.foodtab.service.dto;

import com.mysql.jdbc.Blob;

public class MenuItemDetailsDTO
{
    private Blob blob;

    private Integer rating;

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
