package com.focus.foodtab.library.common;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

public class UtilHelper
{
    public static <FromBean, ToBean> List<ToBean> mapListOfEnitiesToDTOs(DozerBeanMapper mapper, List<FromBean> beans, Class<ToBean> clazz)
    {
        List<ToBean> list = new ArrayList<ToBean>();
        for (FromBean bean : beans)
        {
            if (bean != null)
            {
                list.add(mapper.map(bean, clazz));
            }
        }
        return list;
    }
}
