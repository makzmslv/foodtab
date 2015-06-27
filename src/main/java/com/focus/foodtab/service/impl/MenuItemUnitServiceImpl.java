package com.focus.foodtab.service.impl;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus.foodtab.dto.menuitem.MenuItemUnitDTO;
import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.persistence.dao.MenuItemDAO;
import com.focus.foodtab.persistence.dao.MenuItemUnitDAO;
import com.focus.foodtab.persistence.entity.MenuItemEntity;
import com.focus.foodtab.persistence.entity.MenuItemUnitEntity;
import com.focus.foodtab.service.error.ErrorMessage;
import com.focus.foodtab.service.error.ServerException;

@Service
public class MenuItemUnitServiceImpl
{
    @Autowired
    private MenuItemUnitDAO menuItemUnitDAO;

    @Autowired
    private MenuItemDAO menuItemDAO;

    @Autowired
    private DozerBeanMapper mapper;

    public MenuItemUnitDTO createMenuItemUnit(Integer menuItemId, MenuItemUnitDTO createDTO)
    {
        MenuItemEntity menuItem = getMenuItem(menuItemId);
        MenuItemUnitEntity menuItemUnit = mapper.map(createDTO, MenuItemUnitEntity.class);
        menuItemUnit.setMenuItem(menuItem);
        menuItemUnit = menuItemUnitDAO.save(menuItemUnit);
        return mapper.map(menuItemUnit, MenuItemUnitDTO.class);
    }

    public MenuItemUnitDTO updateMenuItemUnit(Integer menuItemId, Integer menuItemUnitId, MenuItemUnitDTO updateDTO)
    {
        getMenuItem(menuItemId);
        MenuItemUnitEntity menuItemUnit = getMenuItemUnit(menuItemUnitId);
        menuItemUnit = mapper.map(updateDTO, MenuItemUnitEntity.class);
        menuItemUnit = menuItemUnitDAO.save(menuItemUnit);
        return mapper.map(menuItemUnit, MenuItemUnitDTO.class);
    }

    private MenuItemEntity getMenuItem(Integer menuItemId)
    {
        MenuItemEntity menuItem = menuItemDAO.findOne(menuItemId);
        if (menuItem == null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.MENU_ITEM_NOT_FOUND));
        }
        return menuItem;
    }

    private MenuItemUnitEntity getMenuItemUnit(Integer menuItemUnitId)
    {
        MenuItemUnitEntity menuItemUnit = menuItemUnitDAO.findOne(menuItemUnitId);
        if (menuItemUnit == null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.MENU_ITEM_UNIT_NOT_FOUND));
        }
        return menuItemUnit;
    }
}
