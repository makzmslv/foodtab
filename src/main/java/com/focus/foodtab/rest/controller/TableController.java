package com.focus.foodtab.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.focus.foodtab.service.dto.TableDTO;
import com.focus.foodtab.service.impl.TableServiceImpl;
import com.wordnik.swagger.annotations.Api;

@Api(value = "tables", description = "tables")
@Controller
@RequestMapping(value = "/tables")
public class TableController
{
    @Autowired
    private TableServiceImpl tableService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public TableDTO createTable(@Valid @RequestBody TableDTO createDTO)
    {
        return tableService.createTable(createDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public TableDTO updateTable(@PathVariable Integer id, @Valid @RequestBody TableDTO updateDTO)
    {
        return tableService.updateTable(id, updateDTO);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<TableDTO> getTables(@RequestParam(required = false) Boolean active)
    {
        if (active == null)
        {
            return tableService.findAll();
        }
        return tableService.findbyActiveStatus(active);
    }
}
