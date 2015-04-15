package com.focus.foodtab.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus.foodtab.library.enums.CategorySubType;
import com.focus.foodtab.library.enums.CategoryType;
import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.persistence.dao.CategoryDAO;
import com.focus.foodtab.persistence.entity.CategoryEntity;
import com.focus.foodtab.service.dto.CategoryDTO;
import com.focus.foodtab.service.dto.CategoryUpdateDisplayOrderDTO;
import com.focus.foodtab.service.error.ErrorMessage;
import com.focus.foodtab.service.error.ServerException;

@Service
public class CategoryServiceImpl
{
    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private DozerBeanMapper mapper;

    public CategoryDTO createCategory(CategoryDTO createDTO)
    {
        validateInputForCreateDTO(createDTO);
        CategoryEntity category = mapper.map(createDTO, CategoryEntity.class);
        category = categoryDAO.save(category);
        return mapper.map(category, CategoryDTO.class);
    }

    public List<CategoryDTO> findAll()
    {
        List<CategoryEntity> categories = categoryDAO.findAll();
        return mapEntityListToDTOs(categories);
    }

    public List<CategoryDTO> findbyActiveStatus(Boolean active)
    {
        List<CategoryEntity> categories = categoryDAO.findByActive(active);
        return mapEntityListToDTOs(categories);
    }

    public CategoryDTO updateCategory(Integer categoryId, CategoryDTO updateDTO)
    {
        validateUpdateDTO(categoryId, updateDTO);
        CategoryEntity category = getCategory(categoryId);
        category = mapper.map(updateDTO, CategoryEntity.class);
        categoryDAO.save(category);
        return mapper.map(category, CategoryDTO.class);
    }

    public List<CategoryDTO> updateDisplayOrder(List<CategoryUpdateDisplayOrderDTO> updateDTOs)
    {
        List<CategoryDTO> updatedCategories = new ArrayList<CategoryDTO>();
        for (CategoryUpdateDisplayOrderDTO updateDTO : updateDTOs)
        {
            CategoryEntity category1 = getCategory(updateDTO.getCategoryId1());
            CategoryEntity category2 = getCategory(updateDTO.getCategoryId2());
            category1.setDisplayRank(category2.getDisplayRank());
            category2.setDisplayRank(category1.getDisplayRank());
            categoryDAO.save(category1);
            categoryDAO.save(category2);
            updatedCategories.add(mapper.map(category1, CategoryDTO.class));
            updatedCategories.add(mapper.map(category2, CategoryDTO.class));
        }
        return updatedCategories;
    }

    private void validateUpdateDTO(Integer categoryId, CategoryDTO updateDTO)
    {
        getCategory(categoryId);
        validateInputForCreateDTO(updateDTO);
    }

    private CategoryEntity getCategory(Integer categoryId)
    {
        CategoryEntity category = categoryDAO.findOne(categoryId);
        if (category == null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_NOT_FOUND));
        }
        return category;
    }

    private void validateInputForCreateDTO(CategoryDTO createDTO)
    {
        validateType(createDTO.getType());
        validateSubType(createDTO.getType(), createDTO.getSubType());
        checkIfDuplicateEntryExists(createDTO);
        validateDisplayOrder(createDTO.getDisplayRank());
    }

    private void validateType(Integer categoryType)
    {
        if (!CategoryType.getAllTypes().contains(categoryType))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.INVALID_CATEGORY_TYPE));
        }
    }

    private void validateSubType(Integer categoryType, Integer categorySubType)
    {
        if (!CategorySubType.getAllSubTypes().contains(categorySubType))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.INVALID_CATEGORY_SUB_TYPE));
        }

        if (CategoryType.FOOD.getCode().equals(categoryType))
        {
            if (!CategorySubType.getFoodSubTypes().contains(categorySubType))
            {
                throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_TYPE_SUB_TYPE_MISMATCH));
            }
        }

        if (CategoryType.DRINKS.getCode().equals(categoryType))
        {
            if (!CategorySubType.getDrinkSubTypes().contains(categorySubType))
            {
                throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_TYPE_SUB_TYPE_MISMATCH));
            }
        }
    }

    private void checkIfDuplicateEntryExists(CategoryDTO createDTO)
    {
        CategoryEntity category = categoryDAO.findByNameAndTypeAndSubType(createDTO.getName(), createDTO.getType(), createDTO.getSubType());
        if (category != null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_ALREADY_EXISTS));
        }
    }

    private void validateDisplayOrder(Integer displayOrder)
    {
        CategoryEntity category = categoryDAO.findByDisplayRank(displayOrder);
        if (category != null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_TYPE_SUB_TYPE_MISMATCH));
        }
    }

    private List<CategoryDTO> mapEntityListToDTOs(List<CategoryEntity> categories)
    {
        List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
        for (CategoryEntity category : categories)
        {
            CategoryDTO dto = mapper.map(category, CategoryDTO.class);
            categoryDTOs.add(dto);
        }
        return categoryDTOs;
    }
}
