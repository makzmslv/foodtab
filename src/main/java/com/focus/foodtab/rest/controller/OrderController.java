package com.focus.foodtab.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.focus.foodtab.dto.order.OrderCreateDTO;
import com.focus.foodtab.dto.order.OrderDTO;
import com.focus.foodtab.dto.order.OrderDetailsCreateDTO;
import com.focus.foodtab.dto.order.OrderDetailsDTO;
import com.focus.foodtab.dto.order.OrderDetailsUpdateDTO;
import com.focus.foodtab.dto.order.OrderUpdateDTO;
import com.focus.foodtab.service.impl.OrderServiceImpl;
import com.wordnik.swagger.annotations.Api;

@Api(value = "orders", description = "orders")
@Controller
@RequestMapping(value = "/tables/{tableId}/orders")
public class OrderController
{
    @Autowired
    private OrderServiceImpl orderService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public OrderDTO createOrder(@RequestBody OrderCreateDTO createDTO)
    {
        return orderService.createOrder(createDTO);
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public OrderDTO getOrder(@PathVariable Integer orderId)
    {
        return orderService.getOrder(orderId);
    }

    @RequestMapping(value = "/{orderId}/orderDetails", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderDetailsDTO> getOrderItems(Integer orderId)
    {
        return orderService.getOrderItems(orderId);
    }

    @RequestMapping(value = "/{orderId}/orderDetails", method = RequestMethod.POST)
    @ResponseBody
    public List<OrderDetailsDTO> addMenuItemsToOrder(@PathVariable Integer orderId, @Valid @RequestBody List<OrderDetailsCreateDTO> orderDetailscreateDTO)
    {
        return orderService.addMenuItemsToOrder(orderId, orderDetailscreateDTO);
    }

    @RequestMapping(value = "/{orderId}/orderDetails", method = RequestMethod.PUT)
    @ResponseBody
    public List<OrderDetailsDTO> updateOrderItems(Integer orderId, List<OrderDetailsUpdateDTO> orderDetailsUpdateDTO)
    {
        return orderService.updateOrderItems(orderId, orderDetailsUpdateDTO);
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.PUT)
    @ResponseBody
    public OrderDTO updateOrderStatus(@PathVariable Integer orderId, @Valid @RequestBody OrderUpdateDTO updateDTO)
    {
        return orderService.updateOrderStatus(orderId, updateDTO);
    }
}
