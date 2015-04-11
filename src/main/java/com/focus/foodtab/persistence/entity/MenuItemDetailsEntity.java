package com.focus.foodtab.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mysql.jdbc.Blob;

@Entity
@Table(name = "MENU_ITEM_DETAILS")
public class MenuItemDetailsEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "ETA_MINUTES")
    private Integer estimatedTimeInMinutes;

    @Column(name = "RATING")
    private Integer rating;

    @Lob
    @Column(name = "PHOTO")
    private Blob photo;

    @OneToOne
    @JoinColumn(name = "REF_MENU_ITEM")
    private MenuItemEntity menuItem;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Integer getEstimatedTimeInMinutes()
    {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(Integer estimatedTimeInMinutes)
    {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public Integer getRating()
    {
        return rating;
    }

    public void setRating(Integer rating)
    {
        this.rating = rating;
    }

    public Blob getPhoto()
    {
        return photo;
    }

    public void setPhoto(Blob photo)
    {
        this.photo = photo;
    }

    public MenuItemEntity getMenuItem()
    {
        return menuItem;
    }

    public void setMenuItem(MenuItemEntity menuItem)
    {
        this.menuItem = menuItem;
    }

}
