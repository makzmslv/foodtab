package com.focus.foodtab.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus.foodtab.dto.menu.MenuCreateDTO;
import com.focus.foodtab.dto.menu.MenuDTO;
import com.focus.foodtab.dto.menu.MenuUpdateDTO;
import com.focus.foodtab.library.common.UtilHelper;
import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.persistence.dao.CategoryDAO;
import com.focus.foodtab.persistence.dao.MenuDAO;
import com.focus.foodtab.persistence.dao.MenuItemDAO;
import com.focus.foodtab.persistence.entity.CategoryEntity;
import com.focus.foodtab.persistence.entity.MenuEntity;
import com.focus.foodtab.persistence.entity.MenuItemEntity;
import com.focus.foodtab.service.error.ErrorMessage;
import com.focus.foodtab.service.error.ServerException;

@Service
@Transactional
public class MenuServiceImpl
{
    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private MenuItemDAO menuItemDAO;

    @Autowired
    private EntryExistingValidator validator;

    @Autowired
    private DozerBeanMapper mapper;

    public List<MenuDTO> createMenuEntries(MenuCreateDTO createDTO)
    {
        CategoryEntity categoryEntity = validator.getCategoryEntityFromId(createDTO.getCategoryId());
        List<MenuDTO> menuEntries = new ArrayList<MenuDTO>();
        for (Integer menuItemId : createDTO.getMenuItemIds())
        {
            MenuItemEntity menuItemEntity = validator.getMenuItemEntityFromId(menuItemId);
            checkIfInActive(categoryEntity, menuItemEntity);
            checkIfMenuEntryAlreadyExists(categoryEntity, menuItemEntity);
            MenuEntity menuEntity = createMenuEntryEntity(categoryEntity, menuItemEntity);
            menuEntity = menuDAO.save(menuEntity);
            menuEntries.add(mapper.map(menuEntity, MenuDTO.class));
        }
        return menuEntries;
    }

    public MenuDTO updateMenuEntry(Integer menuEntryId, MenuUpdateDTO updateDTO)
    {
        MenuEntity menuEntity = validator.getMenuEntityFromId(menuEntryId);
        checkIfFieldsAreUpdated(menuEntity, updateDTO);
        CategoryEntity categoryEntity = validator.getCategoryEntityFromId(updateDTO.getCategoryId());
        MenuItemEntity menuItemEntity = validator.getMenuItemEntityFromId(updateDTO.getMenuItemId());
        checkIfInActive(categoryEntity, menuItemEntity);
        checkIfMenuEntryAlreadyExists(categoryEntity, menuItemEntity);
        menuEntity.setCategory(categoryEntity);
        menuEntity.setMenuItem(menuItemEntity);
        menuEntity = menuDAO.save(menuEntity);
        return mapper.map(menuEntity, MenuDTO.class);
    }

    public List<MenuDTO> getMenuEntries()
    {
        List<MenuEntity> menuEntries = menuDAO.findAll();
        return UtilHelper.mapListOfEnitiesToDTOs(mapper, menuEntries, MenuDTO.class);
    }

    public void deleteMenuEntry(Integer menuEntryId)
    {
        MenuEntity menuEntity = validator.getMenuEntityFromId(menuEntryId);
        menuDAO.delete(menuEntity);
    }

    private void checkIfInActive(CategoryEntity categoryEntity, MenuItemEntity menuItemEntity)
    {
        if (!categoryEntity.getActive())
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_INACTIVE));
        }
        if (!menuItemEntity.getActive())
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.MENU_ITEM_INACTIVE));
        }
    }

    private void checkIfMenuEntryAlreadyExists(CategoryEntity categoryEntity, MenuItemEntity menuItemEntity)
    {
        MenuEntity menuEntity = menuDAO.findByMenuItemAndCategory(menuItemEntity, categoryEntity);
        if (menuEntity != null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.MENU_ENTRY_ALREADY_EXISTS));
        }
    }

    private void checkIfFieldsAreUpdated(MenuEntity menuEntity, MenuUpdateDTO updateDTO)
    {
        if (menuEntity.getCategory().getId() == updateDTO.getCategoryId() && menuEntity.getMenuItem().getId() == updateDTO.getMenuItemId())
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.NO_FIELDS_UPDATED));
        }

    }

    private MenuEntity createMenuEntryEntity(CategoryEntity categoryEntity, MenuItemEntity menuItemEntity)
    {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setCategory(categoryEntity);
        menuEntity.setMenuItem(menuItemEntity);
        return menuEntity;
    }
}
