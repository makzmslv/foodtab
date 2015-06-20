package com.focus.foodtab.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.persistence.dao.MenuDAO;
import com.focus.foodtab.persistence.dao.MenuItemDAO;
import com.focus.foodtab.persistence.entity.MenuEntity;
import com.focus.foodtab.persistence.entity.MenuItemEntity;
import com.focus.foodtab.persistence.entity.MenuItemUnitEntity;
import com.focus.foodtab.service.dto.MenuItemDTO;
import com.focus.foodtab.service.error.ErrorMessage;
import com.focus.foodtab.service.error.ServerException;

@Service
public class MenuItemServiceImpl
{
    @Autowired
    private MenuItemDAO menuItemDAO;

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private DozerBeanMapper mapper;

    public MenuItemDTO createMenuItem(MenuItemDTO createDTO)
    {
        validateCreateDTO(createDTO);
        MenuItemEntity menuItem = mapper.map(createDTO, MenuItemEntity.class);
        setReferences(menuItem);
        menuItem = menuItemDAO.save(menuItem);
        return mapper.map(menuItem, MenuItemDTO.class);
    }

    public MenuItemDTO updateMenuItem(Integer menuItemId, MenuItemDTO updateDTO)
    {
        MenuItemEntity menuItem = getMenuItem(menuItemId);
        validateUpdateDTO(updateDTO, menuItem);
        menuItem = mapper.map(updateDTO, MenuItemEntity.class);
        menuItemDAO.save(menuItem);
        return mapper.map(menuItem, MenuItemDTO.class);
    }

    public List<MenuItemDTO> findAll()
    {
        List<MenuItemEntity> menuItems = menuItemDAO.findAll();
        return mapEntityListToDTOs(menuItems);
    }

    public List<MenuItemDTO> findbyActiveStatus(Boolean active)
    {
        List<MenuItemEntity> menuItems = menuItemDAO.findByActive(active);
        return mapEntityListToDTOs(menuItems);
    }

    public void deleteMenuItem(Integer menuItemId)
    {
        MenuItemEntity menuItem = getMenuItem(menuItemId);
        List<MenuEntity> itemsInMenu = menuDAO.findByMenuItem(menuItem);
        for (MenuEntity item : itemsInMenu)
        {
            menuDAO.delete(item);
        }
        menuItemDAO.delete(menuItem);
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

    private void validateCreateDTO(MenuItemDTO createDTO)
    {
        validateName(createDTO.getName());
        validateCode(createDTO.getCode());
    }

    private void validateUpdateDTO(MenuItemDTO updateDTO, MenuItemEntity menuItem)
    {
        if (menuItem.getName() != updateDTO.getName())
        {
            validateName(updateDTO.getName());
        }
        if (menuItem.getCode() != updateDTO.getCode())
        {
            validateCode(updateDTO.getCode());
        }
    }

    private void validateName(String name)
    {
        MenuItemEntity menuItem = menuItemDAO.findByName(name);
        if (menuItem != null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.MENU_ITEM_ALREADY_EXISTS));
        }
    }

    private void validateCode(Integer code)
    {
        MenuItemEntity menuItem = menuItemDAO.findByCode(code);
        if (menuItem != null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.DUPLICATE_CODE_FOR_MENU_ITEM));
        }
    }

    private void setReferences(MenuItemEntity menuItem)
    {
        menuItem.getMenuItemDetails().setMenuItem(menuItem);
        for (MenuItemUnitEntity menuItemUnit : menuItem.getMenuItemUnits())
        {
            menuItemUnit.setMenuItem(menuItem);
        }
    }

    private List<MenuItemDTO> mapEntityListToDTOs(List<MenuItemEntity> menuItems)
    {
        List<MenuItemDTO> menuItemDTOs = new ArrayList<MenuItemDTO>();
        for (MenuItemEntity menuItem : menuItems)
        {
            MenuItemDTO dto = mapper.map(menuItem, MenuItemDTO.class);
            menuItemDTOs.add(dto);
        }
        return menuItemDTOs;
    }

}
