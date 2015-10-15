package com.focus.foodtab.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.focus.foodtab.dto.bill.BillCreateDTO;
import com.focus.foodtab.dto.bill.BillDTO;
import com.focus.foodtab.service.impl.BillServiceImpl;
import com.wordnik.swagger.annotations.Api;

@Api(value = "bill", description = "bill")
@Controller
@RequestMapping(value = "/tables/{tableId}/orders/{orderId}/bill")
public class BillController
{
    @Autowired
    private BillServiceImpl billService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public BillDTO createMenuEntry(@Valid @RequestBody BillCreateDTO createDTO)
    {
        return billService.createBill(createDTO);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public BillDTO getOrder(@PathVariable Integer orderId)
    {
        return billService.getBillForOrder(orderId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public BillDTO recalculateBill(@PathVariable Integer orderId)
    {
        return billService.recalculateBill(orderId);
    }
}
