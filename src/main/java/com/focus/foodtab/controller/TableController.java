package com.focus.foodtab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.focus.foodtab.service.dto.TableDTO;
import com.focus.foodtab.service.impl.TableServiceImpl;

@Controller
@RequestMapping(value = "/table")
public class TableController
{
    @Autowired
    private TableServiceImpl tableService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public TableDTO createTable(@RequestBody TableDTO createDTO)
    {
        return tableService.createTable(createDTO);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public TableDTO updateTable(@RequestBody TableDTO updateDTO)
    {
        return tableService.updateTable(updateDTO);
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
