package com.focus.foodtab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.persistence.dao.CategoryDAO;
import com.focus.foodtab.persistence.dao.MenuDAO;
import com.focus.foodtab.persistence.dao.MenuItemDAO;
import com.focus.foodtab.persistence.dao.OrderDAO;
import com.focus.foodtab.persistence.dao.OrderDetailsDAO;
import com.focus.foodtab.persistence.dao.TableDAO;
import com.focus.foodtab.persistence.entity.CategoryEntity;
import com.focus.foodtab.persistence.entity.MenuEntity;
import com.focus.foodtab.persistence.entity.MenuItemEntity;
import com.focus.foodtab.persistence.entity.OrderDetailsEntity;
import com.focus.foodtab.persistence.entity.OrderEntity;
import com.focus.foodtab.persistence.entity.TableEntity;
import com.focus.foodtab.service.error.ErrorMessage;
import com.focus.foodtab.service.error.ServerException;

@Service
public class EntryExistingValidator
{
    @Autowired
    private TableDAO tableDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private MenuItemDAO menuItemDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderDetailsDAO orderDetailsDAO;

    public CategoryEntity getCategoryEntityFromId(Integer categoryId)
    {
        CategoryEntity category = categoryDAO.findOne(categoryId);
        if (category == null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_NOT_FOUND));
        }
        return category;
    }

    public MenuItemEntity getMenuItemEntityFromId(Integer menuItemId)
    {
        MenuItemEntity menuItem = menuItemDAO.findOne(menuItemId);
        if (menuItem == null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.MENU_ITEM_NOT_FOUND));
        }
        return menuItem;
    }

    public MenuEntity getMenuEntityFromId(Integer menuEntryId)
    {
        MenuEntity menuEntry = menuDAO.findOne(menuEntryId);
        if (menuEntry == null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.MENU_ENTRY_NOT_FOUND));
        }
        return menuEntry;
    }

    public TableEntity getTableEntityFromTableNo(Integer tableNo)
    {
        TableEntity tableEntity = tableDAO.findByTableNo(tableNo);
        if (tableEntity == null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.TABLE_NOT_FOUND));
        }
        return tableEntity;
    }

    public OrderEntity getOrderEntityFromId(Integer orderId)
    {
        OrderEntity orderEntity = orderDAO.findOne(orderId);
        if (orderEntity == null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.ORDER_DOES_NOT_EXIST));
        }
        return orderEntity;
    }

    public OrderDetailsEntity getOrderDetailsEntityId(Integer orderDetailsId)
    {
        OrderDetailsEntity orderDetailsEntity = orderDetailsDAO.findOne(orderDetailsId);
        if (orderDetailsEntity == null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.ORDER_DOES_NOT_EXIST));
        }
        return orderDetailsEntity;
    }
}
