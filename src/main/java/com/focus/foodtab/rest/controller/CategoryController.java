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

import com.focus.foodtab.service.dto.CategoryDTO;
import com.focus.foodtab.service.dto.CategoryUpdateDisplayOrderDTO;
import com.focus.foodtab.service.impl.CategoryServiceImpl;

@Controller
@RequestMapping(value = "/categories")
public class CategoryController
{
    @Autowired
    private CategoryServiceImpl categoryService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CategoryDTO createCategory(@RequestBody CategoryDTO createDTO)
    {
        return categoryService.createCategory(createDTO);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<CategoryDTO> getTables(@RequestParam(required = false) Boolean active)
    {
        if (active == null)
        {
            return categoryService.findAll();
        }
        return categoryService.findbyActiveStatus(active);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CategoryDTO updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO updateDTO)
    {
        return categoryService.updateCategory(id, updateDTO);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public List<CategoryDTO> updateDisplayOrder(@RequestBody List<CategoryUpdateDisplayOrderDTO> updateDTOs)
    {
        return categoryService.updateDisplayOrder(updateDTOs);
    }
}
