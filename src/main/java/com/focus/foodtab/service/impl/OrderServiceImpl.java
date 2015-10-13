package com.focus.foodtab.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus.foodtab.dto.order.OrderDTO;
import com.focus.foodtab.dto.order.OrderDetailsCreateDTO;
import com.focus.foodtab.dto.order.OrderDetailsDTO;
import com.focus.foodtab.dto.order.OrderDetailsUpdateDTO;
import com.focus.foodtab.library.common.UtilHelper;
import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.library.enums.OrderItemStatus;
import com.focus.foodtab.library.enums.OrderStatus;
import com.focus.foodtab.persistence.dao.OrderDAO;
import com.focus.foodtab.persistence.dao.OrderDetailsDAO;
import com.focus.foodtab.persistence.entity.MenuItemEntity;
import com.focus.foodtab.persistence.entity.OrderDetailsEntity;
import com.focus.foodtab.persistence.entity.OrderEntity;
import com.focus.foodtab.persistence.entity.TableEntity;
import com.focus.foodtab.service.error.ErrorMessage;
import com.focus.foodtab.service.error.ServerException;

@Service
@Transactional
public class OrderServiceImpl
{
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderDetailsDAO orderDetailsDAO;

    @Autowired
    private EntryExistingValidator validator;

    @Autowired
    private DozerBeanMapper mapper;

    public OrderDTO createOrder(Integer tableNo)
    {
        TableEntity tableEntity = validator.getTableEntityFromTableNo(tableNo);
        checkIfOrderAlreadyInProgressForTable(tableEntity);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(OrderStatus.CREATED.getCode());
        orderEntity.setTable(tableEntity);
        orderEntity = orderDAO.save(orderEntity);
        return mapper.map(orderEntity, OrderDTO.class);
    }

    public OrderDTO getOrder(Integer orderId)
    {
        OrderEntity orderEntity = validator.getOrderEntityFromId(orderId);
        return mapper.map(orderEntity, OrderDTO.class);
    }

    public List<OrderDetailsDTO> addMenuItemsToOrder(Integer orderId, List<OrderDetailsCreateDTO> orderDetailscreateDTO)
    {
        if (orderDetailscreateDTO.isEmpty())
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.NO_FIELDS_UPDATED));
        }
        OrderEntity orderEntity = validator.getOrderEntityFromId(orderId);
        checkIfOrderAlreadyCompleted(orderEntity);
        List<OrderDetailsDTO> orderDetailsDTOs = new ArrayList<OrderDetailsDTO>();
        for (OrderDetailsCreateDTO orderItem : orderDetailscreateDTO)
        {
            MenuItemEntity menuItemEntity = validator.getMenuItemEntityFromId(orderItem.getMenuItemId());
            Integer cost = getCostOfMenuItem(menuItemEntity.getPrice(), orderItem.getQuantity());
            OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();
            orderDetailsEntity.setCostOfItem(cost);
            orderDetailsEntity.setMenuItem(menuItemEntity);
            orderDetailsEntity.setOrder(orderEntity);
            orderDetailsEntity.setQuantity(orderItem.getQuantity());
            orderDetailsEntity.setTimestamp(DateTime.now().toDate());
            orderDetailsEntity.setStatus(OrderItemStatus.PLACED.getCode());
            orderDetailsEntity = orderDetailsDAO.save(orderDetailsEntity);
            orderDetailsDTOs.add(mapper.map(orderDetailsEntity, OrderDetailsDTO.class));
        }
        updateOrderStatus(orderEntity);
        return orderDetailsDTOs;
    }

    public List<OrderDetailsDTO> updateOrderItems(Integer orderId, List<OrderDetailsUpdateDTO> orderDetailsUpdateDTO)
    {
        if (orderDetailsUpdateDTO.isEmpty())
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.NO_FIELDS_UPDATED));
        }
        OrderEntity orderEntity = validator.getOrderEntityFromId(orderId);
        checkIfOrderAlreadyCompleted(orderEntity);
        List<OrderDetailsDTO> orderDetailsDTOs = new ArrayList<OrderDetailsDTO>();
        for (OrderDetailsUpdateDTO orderItem : orderDetailsUpdateDTO)
        {
            OrderDetailsEntity orderDetailsEntity = validator.getOrderDetailsEntityId(orderItem.getOrderDetailsId());
            checkIfOrderItemCanBeUpdated(orderDetailsEntity, orderItem.getStatus());
            orderDetailsEntity.setStatus(orderItem.getStatus());
            orderDetailsEntity = orderDetailsDAO.save(orderDetailsEntity);
            orderDetailsDTOs.add(mapper.map(orderDetailsEntity, OrderDetailsDTO.class));
        }
        return orderDetailsDTOs;
    }

    public List<OrderDetailsDTO> getOrderItems(Integer orderId)
    {
        OrderEntity orderEntity = validator.getOrderEntityFromId(orderId);
        List<OrderDetailsEntity> orderItems = orderDetailsDAO.findByOrder(orderEntity);
        return UtilHelper.mapListOfEnitiesToDTOs(mapper, orderItems, OrderDetailsDTO.class);
    }

    private void checkIfOrderAlreadyInProgressForTable(TableEntity tableEntity)
    {
        OrderEntity orderEntity = orderDAO.findByTableAndStatusNot(tableEntity, OrderStatus.BILL_PAID.getCode());
        if (orderEntity != null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.ORDER_ALREADY_IN_PROGRESS_FOR_TABLE));
        }
    }

    private void checkIfOrderAlreadyCompleted(OrderEntity orderEntity)
    {
        if (orderEntity.getStatus().equals(OrderStatus.BILL_PAID))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.ORDER_COMPLETED));
        }
    }

    private void checkIfOrderItemCanBeUpdated(OrderDetailsEntity orderDetailsEntity, Integer orderStatus)
    {
        if (orderDetailsEntity.getStatus().equals(OrderItemStatus.CANCELLED))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.ORDER_ITEM_CANNOT_UPDATED_AS_ALREADY_CANCELLED));
        }
        if (orderDetailsEntity.getStatus().equals(OrderItemStatus.DELIVERED))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.ORDER_ITEM_CANNOT_UPDATED_AS_ALREADY_DELIVERED));
        }
        if (OrderItemStatus.CANCELLED.getCode().equals(orderStatus))
        {
            DateTime timeOfOrder = new DateTime(orderDetailsEntity.getTimestamp());
            DateTime currentTime = DateTime.now();
            DateTime timeAfterWhichOrderCannotBeCancelled = timeOfOrder.plusMinutes(5);
            if (currentTime.isAfter(timeAfterWhichOrderCannotBeCancelled))
            {
                throw new ServerException(new ErrorMessage(ErrorCodes.ORDER_ITEM_CANNOT_UPDATED_DUE_TO_TIME_ELAPSED));
            }
        }
    }

    private void updateOrderStatus(OrderEntity orderEntity)
    {
        orderEntity.setStatus(OrderStatus.ITEMS_ORDERED.getCode());
        orderDAO.save(orderEntity);
    }

    private Integer getCostOfMenuItem(Integer price, Integer quantity)
    {
        return price * quantity;
    }
}
