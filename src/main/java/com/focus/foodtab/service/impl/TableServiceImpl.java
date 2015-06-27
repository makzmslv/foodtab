package com.focus.foodtab.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus.foodtab.dto.table.TableCreateDTO;
import com.focus.foodtab.dto.table.TableDTO;
import com.focus.foodtab.dto.table.TableUpdateDTO;
import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.persistence.dao.TableDAO;
import com.focus.foodtab.persistence.entity.TableEntity;
import com.focus.foodtab.service.error.ErrorMessage;
import com.focus.foodtab.service.error.ServerException;

@Service
public class TableServiceImpl
{
    @Autowired
    private TableDAO tableDAO;

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
        return mapEntityListToDTOs(tables);
    }

    public List<TableDTO> findbyActiveStatus(Boolean active)
    {
        List<TableEntity> tables = tableDAO.findByActive(active);
        return mapEntityListToDTOs(tables);
    }

    private List<TableDTO> mapEntityListToDTOs(List<TableEntity> tables)
    {
        List<TableDTO> tableCreateDTOs = new ArrayList<TableDTO>();
        for (TableEntity table : tables)
        {
            TableDTO dto = mapper.map(table, TableDTO.class);
            tableCreateDTOs.add(dto);
        }
        return tableCreateDTOs;
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
        TableEntity tableEntity = tableDAO.findByTableNo(tableNo);

        if (tableEntity == null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.TABLE_NOT_FOUND));
        }

        if (tableEntity.getActive().equals(updateDTO.getActive()))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.NO_FIELDS_UPDATED));
        }

    }
}
