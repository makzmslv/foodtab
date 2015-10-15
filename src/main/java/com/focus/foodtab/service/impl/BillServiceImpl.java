package com.focus.foodtab.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus.foodtab.dto.bill.BillCreateDTO;
import com.focus.foodtab.dto.bill.BillDTO;
import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.library.enums.OrderStatus;
import com.focus.foodtab.library.enums.ParameterKeys;
import com.focus.foodtab.persistence.dao.BillDAO;
import com.focus.foodtab.persistence.dao.ParameterDAO;
import com.focus.foodtab.persistence.entity.BillEntity;
import com.focus.foodtab.persistence.entity.OrderDetailsEntity;
import com.focus.foodtab.persistence.entity.OrderEntity;
import com.focus.foodtab.service.error.ErrorMessage;
import com.focus.foodtab.service.error.ServerException;

@Service
@Transactional
public class BillServiceImpl
{
    @Autowired
    private BillDAO billDAO;

    @Autowired
    private ParameterDAO parameterDAO;

    @Autowired
    private EntryExistingValidator validator;

    @Autowired
    private DozerBeanMapper mapper;

    public BillDTO createBill(BillCreateDTO createDTO)
    {
        OrderEntity orderEntity = validator.getOrderEntityFromId(createDTO.getOrderId());
        validateInput(orderEntity);
        BillEntity bill = new BillEntity();
        bill.setTable(orderEntity.getTable());
        bill.setOrder(orderEntity);
        prepareBillEntity(bill);
        bill = billDAO.save(bill);
        orderEntity.setStatus(OrderStatus.BILL_GENERATED.getCode());
        return mapper.map(bill, BillDTO.class);
    }

    public BillDTO getBillForOrder(Integer orderId)
    {
        OrderEntity orderEntity = validator.getOrderEntityFromId(orderId);
        BillEntity bill = billDAO.findByOrder(orderEntity);
        return mapper.map(bill, BillDTO.class);
    }

    public BillDTO recalculateBill(Integer orderId)
    {
        OrderEntity orderEntity = validator.getOrderEntityFromId(orderId);
        if (OrderStatus.ORDER_COMPLETED.equals(orderEntity.getStatus()))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.INVALID_ORDER_STATUS));
        }
        BillEntity bill = billDAO.findByOrder(orderEntity);
        prepareBillEntity(bill);
        bill = billDAO.save(bill);
        orderEntity.setStatus(OrderStatus.BILL_GENERATED.getCode());
        return mapper.map(bill, BillDTO.class);
    }

    private void validateInput(OrderEntity orderEntity)
    {
        if (!OrderStatus.ORDER_COMPLETED.equals(orderEntity.getStatus()))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.INVALID_ORDER_STATUS));
        }
        if (OrderStatus.getBillGeneratedForOrderStatuses().contains(orderEntity.getStatus()))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.BILL_ALREADY_EXISTS));
        }
    }

    private void prepareBillEntity(BillEntity bill)
    {
        List<OrderDetailsEntity> orderItems = bill.getOrder().getOrderDetails();
        BigDecimal billAmount = BigDecimal.ZERO;
        for (OrderDetailsEntity orderItem : orderItems)
        {
            BigDecimal costOfItem = new BigDecimal(orderItem.getCostOfItem());
            billAmount.add(costOfItem);
        }
        String taxValue = parameterDAO.findByKey(ParameterKeys.TAX.getKey()).getValue();
        BigDecimal taxToApply = new BigDecimal(taxValue).divide(BigDecimal.TEN);
        BigDecimal taxAmount = billAmount.multiply(taxToApply);
        BigDecimal totalAmount = billAmount.add(taxAmount);
        bill.setBillAmount(billAmount);
        bill.setTaxAmount(taxAmount);
        bill.setTaxApplied(taxToApply);
        bill.setTotalAmount(totalAmount);
        bill.setTimestamp(DateTime.now().toDate());
    }
}
