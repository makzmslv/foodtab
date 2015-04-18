package com.focus.foodtab.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.focus.foodtab.service.dto.MenuItemDTO;
import com.focus.foodtab.service.dto.MenuItemUnitDTO;
import com.focus.foodtab.service.impl.MenuItemServiceImpl;
import com.focus.foodtab.service.impl.MenuItemUnitServiceImpl;
import com.wordnik.swagger.annotations.Api;

@Api(value = "menuItems", description = "menuItems")
@Controller
@RequestMapping(value = "/menuItems")
public class MenuItemController
{
    @Autowired
    private MenuItemServiceImpl menuItemService;

    @Autowired
    private MenuItemUnitServiceImpl menuItemUnitService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public MenuItemDTO createMenuItem(@RequestBody MenuItemDTO createDTO)
    {
        return menuItemService.createMenuItem(createDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public MenuItemDTO updateMenuItem(@PathVariable Integer id, @RequestBody MenuItemDTO updateDTO)
    {
        return menuItemService.updateMenuItem(id, updateDTO);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<MenuItemDTO> getTables(@RequestParam(required = false) Boolean active)
    {
        if (active == null)
        {
            return menuItemService.findAll();
        }
        return menuItemService.findbyActiveStatus(active);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteMenuItem(@RequestBody Integer menuItemId)
    {
        menuItemService.deleteMenuItem(menuItemId);
    }

    @RequestMapping(value = "/{id}/menuitemunits", method = RequestMethod.POST)
    @ResponseBody
    public MenuItemUnitDTO createMenuItemUnit(@PathVariable Integer id, @RequestBody MenuItemUnitDTO createDTO)
    {
        return menuItemUnitService.createMenuItemUnit(id, createDTO);
    }

    @RequestMapping(value = "/{id}/menuitemunits/{unitId}", method = RequestMethod.POST)
    @ResponseBody
    public MenuItemUnitDTO updateMenuItemUnit(@PathVariable Integer id, Integer unitId, @RequestBody MenuItemUnitDTO createDTO)
    {
        return menuItemUnitService.updateMenuItemUnit(id, unitId, createDTO);
    }
}
