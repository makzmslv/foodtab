package com.focus.foodtab.service.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus.foodtab.dto.order.OrderDTO;
import com.focus.foodtab.dto.table.TableCreateDTO;
import com.focus.foodtab.dto.table.TableDTO;
import com.focus.foodtab.dto.table.TableUpdateDTO;
import com.focus.foodtab.library.common.UtilHelper;
import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.persistence.dao.OrderDAO;
import com.focus.foodtab.persistence.dao.TableDAO;
import com.focus.foodtab.persistence.entity.OrderEntity;
import com.focus.foodtab.persistence.entity.TableEntity;
import com.focus.foodtab.service.error.ErrorMessage;
import com.focus.foodtab.service.error.ServerException;

@Service
@Transactional
public class TableServiceImpl
{
    @Autowired
    private TableDAO tableDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private EntryExistingValidator validator;

    @Autowired
    private DozerBeanMapper mapper;

    public TableDTO createTable(TableCreateDTO createDTO)
    {
        validateCreateTableInput(createDTO);
        TableEntity tableEntity = mapper.map(createDTO, TableEntity.class);
        tableEntity = tableDAO.save(tableEntity);
        return mapper.map(tableEntity, TableDTO.class);
    }

    public TableDTO updateTable(Integer tableNo, TableUpdateDTO updateDTO)
    {
        validateUpdateTableInput(tableNo, updateDTO);
        TableEntity tableEntity = tableDAO.findByTableNo(tableNo);
        tableEntity.setActive(updateDTO.getActive());
        tableEntity = tableDAO.save(tableEntity);
        return mapper.map(tableEntity, TableDTO.class);
    }

    public List<TableDTO> findAll()
    {
        List<TableEntity> tables = tableDAO.findAll();
        return UtilHelper.mapListOfEnitiesToDTOs(mapper, tables, TableDTO.class);
    }

    public List<TableDTO> findbyActiveStatus(Boolean active)
    {
        List<TableEntity> tables = tableDAO.findByActive(active);
        return UtilHelper.mapListOfEnitiesToDTOs(mapper, tables, TableDTO.class);
    }

    public List<OrderDTO> getAllOrdersForTable(Integer tableNo)
    {
        TableEntity tableEntity = validator.getTableEntityFromTableNo(tableNo);
        List<OrderEntity> orderEntities = orderDAO.findByTableEntityOrderByIdDesc(tableEntity);
        return UtilHelper.mapListOfEnitiesToDTOs(mapper, orderEntities, OrderDTO.class);
    }

    private void validateCreateTableInput(TableCreateDTO createDTO)
    {
        TableEntity tableEntity = tableDAO.findByTableNo(createDTO.getTableNo());
        if (tableEntity != null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.TABLE_ALREADY_EXISTS));
        }

    }

    private void validateUpdateTableInput(Integer tableNo, TableUpdateDTO updateDTO)
    {
        TableEntity tableEntity = validator.getTableEntityFromTableNo(tableNo);

        if (tableEntity.getActive().equals(updateDTO.getActive()))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.NO_FIELDS_UPDATED));
        }

    }
}
