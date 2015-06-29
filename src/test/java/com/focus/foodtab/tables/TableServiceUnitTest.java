package com.focus.foodtab.tables;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.focus.foodtab.dbunit.AbstractDbUnit;
import com.focus.foodtab.dbunit.SetUpDataSet;
import com.focus.foodtab.dto.table.TableCreateDTO;
import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.service.error.ServerException;
import com.focus.foodtab.service.impl.TableServiceImpl;

public class TableServiceUnitTest extends AbstractDbUnit
{
    @Autowired
    private TableServiceImpl tableServiceImpl;

    @Test
    @SetUpDataSet(xmlPath = "tables/01_TableAlreadyExists.xml", setReplacements = true)
    public void T1_TableAlreadyExists_Error() throws Exception
    {
        TableCreateDTO createDTO = new TableCreateDTO();
        createDTO.setActive(true);
        createDTO.setTableNo(1);
        Exception e = null;
        try
        {
            tableServiceImpl.createTable(createDTO);
        }
        catch (ServerException exception)
        {
            e = exception;
            Assert.assertEquals(ErrorCodes.TABLE_ALREADY_EXISTS.getErrorCode(), exception.getErrorMessage().getErrorCode());
        }
        Assert.assertNotNull(e);
    }
}
