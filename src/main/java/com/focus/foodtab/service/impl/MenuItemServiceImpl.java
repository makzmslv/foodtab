package com.focus.foodtab.service.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus.foodtab.dto.menuitem.MenuItemCreateDTO;
import com.focus.foodtab.dto.menuitem.MenuItemDTO;
import com.focus.foodtab.dto.menuitem.MenuItemUpdateActiveStatusDTO;
import com.focus.foodtab.dto.menuitem.MenuItemUpdateDTO;
import com.focus.foodtab.library.common.UtilHelper;
import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.persistence.dao.MenuDAO;
import com.focus.foodtab.persistence.dao.MenuItemDAO;
import com.focus.foodtab.persistence.entity.MenuEntity;
import com.focus.foodtab.persistence.entity.MenuItemEntity;
import com.focus.foodtab.persistence.entity.MenuItemUnitEntity;
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
    private EntryExistingValidator validator;

    @Autowired
    private DozerBeanMapper mapper;

    public MenuItemDTO createMenuItem(MenuItemCreateDTO createDTO)
    {
        validateCreateDTO(createDTO);
        MenuItemEntity menuItem = mapper.map(createDTO, MenuItemEntity.class);
        setReferences(menuItem);
        menuItem = menuItemDAO.save(menuItem);
        return mapper.map(menuItem, MenuItemDTO.class);
    }

    public MenuItemDTO updateMenuItemDetails(Integer menuItemId, MenuItemUpdateDTO updateDTO)
    {
        MenuItemEntity menuItem = validator.getMenuItemEntityFromId(menuItemId);
        validateUpdateDTO(updateDTO, menuItem);
        mapper.map(menuItem, updateDTO);
        menuItemDAO.save(menuItem);
        return mapper.map(menuItem, MenuItemDTO.class);
    }

    public MenuItemDTO updateMenuItemActiveStatus(Integer menuItemId, MenuItemUpdateActiveStatusDTO updateDTO)
    {
        MenuItemEntity menuItem = validator.getMenuItemEntityFromId(menuItemId);
        List<MenuEntity> menuEntries = menuDAO.findByMenuItem(menuItem);
        if (!menuEntries.isEmpty())
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.MENU_ITEM_IN_USE));
        }
        menuItem.setActive(updateDTO.getActive());
        menuItemDAO.save(menuItem);
        return mapper.map(menuItem, MenuItemDTO.class);
    }

    public List<MenuItemDTO> findAll()
    {
        List<MenuItemEntity> menuItems = menuItemDAO.findAll();
        return UtilHelper.mapListOfEnitiesToDTOs(mapper, menuItems, MenuItemDTO.class);
    }

    public List<MenuItemDTO> findbyActiveStatus(Boolean active)
    {
        List<MenuItemEntity> menuItems = menuItemDAO.findByActive(active);
        return UtilHelper.mapListOfEnitiesToDTOs(mapper, menuItems, MenuItemDTO.class);
    }

    public void deleteMenuItem(Integer menuItemId)
    {
        MenuItemEntity menuItem = validator.getMenuItemEntityFromId(menuItemId);
        List<MenuEntity> itemsInMenu = menuDAO.findByMenuItem(menuItem);
        for (MenuEntity item : itemsInMenu)
        {
            menuDAO.delete(item);
        }
        menuItemDAO.delete(menuItem);
    }

    private void validateCreateDTO(MenuItemCreateDTO createDTO)
    {
        validateName(createDTO.getName());
        validateCode(createDTO.getCode());
    }

    private void validateUpdateDTO(MenuItemUpdateDTO updateDTO, MenuItemEntity menuItem)
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
}
