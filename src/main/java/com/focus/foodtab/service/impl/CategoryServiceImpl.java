package com.focus.foodtab.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus.foodtab.dto.category.CategoryCreateDTO;
import com.focus.foodtab.dto.category.CategoryDTO;
import com.focus.foodtab.dto.category.CategoryUpdateActiveStatusDTO;
import com.focus.foodtab.dto.category.CategoryUpdateDTO;
import com.focus.foodtab.dto.category.CategoryUpdateDisplayOrderDTO;
import com.focus.foodtab.library.common.UtilHelper;
import com.focus.foodtab.library.enums.CategorySubType;
import com.focus.foodtab.library.enums.CategoryType;
import com.focus.foodtab.library.enums.ErrorCodes;
import com.focus.foodtab.persistence.dao.CategoryDAO;
import com.focus.foodtab.persistence.dao.MenuDAO;
import com.focus.foodtab.persistence.entity.CategoryEntity;
import com.focus.foodtab.persistence.entity.MenuEntity;
import com.focus.foodtab.service.error.ErrorMessage;
import com.focus.foodtab.service.error.ServerException;

@Service
public class CategoryServiceImpl
{
    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private EntryExistingValidator validator;

    @Autowired
    private DozerBeanMapper mapper;

    public CategoryDTO createCategory(CategoryCreateDTO createDTO)
    {
        validateInputForCreateDTO(createDTO);
        CategoryEntity category = mapper.map(createDTO, CategoryEntity.class);
        category = categoryDAO.save(category);
        return mapper.map(category, CategoryDTO.class);
    }

    public List<CategoryDTO> findAll()
    {
        List<CategoryEntity> categories = categoryDAO.findAll();
        return UtilHelper.mapListOfEnitiesToDTOs(mapper, categories, CategoryDTO.class);
    }

    public List<CategoryDTO> findbyActiveStatus(Boolean active)
    {
        List<CategoryEntity> categories = categoryDAO.findByActive(active);
        return UtilHelper.mapListOfEnitiesToDTOs(mapper, categories, CategoryDTO.class);
    }

    public CategoryDTO updateCategoryDetails(Integer categoryId, CategoryUpdateDTO updateDTO)
    {
        validateUpdateDTO(categoryId, updateDTO);
        CategoryEntity category = validator.getCategoryEntityFromId(categoryId);
        mapper.map(updateDTO, category);
        categoryDAO.save(category);
        return mapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO updateCategoryActiveStatus(Integer categoryId, CategoryUpdateActiveStatusDTO updateDTO)
    {
        CategoryEntity categoryEntity = validator.getCategoryEntityFromId(categoryId);
        List<MenuEntity> menuEntries = menuDAO.findByCategory(categoryEntity);
        if (!menuEntries.isEmpty())
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_IN_USE));
        }
        categoryEntity.setActive(updateDTO.getActive());
        categoryDAO.save(categoryEntity);
        return mapper.map(categoryEntity, CategoryDTO.class);
    }

    public List<CategoryDTO> updateDisplayOrder(List<CategoryUpdateDisplayOrderDTO> updateDTOs)
    {
        List<CategoryDTO> updatedCategories = new ArrayList<CategoryDTO>();
        for (CategoryUpdateDisplayOrderDTO updateDTO : updateDTOs)
        {
            CategoryEntity category1 = validator.getCategoryEntityFromId(updateDTO.getCategoryId1());
            CategoryEntity category2 = validator.getCategoryEntityFromId(updateDTO.getCategoryId2());
            Integer displayOrderCategory1 = category1.getDisplayRank();
            Integer displayOrderCategory2 = category2.getDisplayRank();
            category1.setDisplayRank(displayOrderCategory2);
            category2.setDisplayRank(displayOrderCategory1);
            categoryDAO.save(category1);
            categoryDAO.save(category2);
            updatedCategories.add(mapper.map(category1, CategoryDTO.class));
            updatedCategories.add(mapper.map(category2, CategoryDTO.class));
        }
        return updatedCategories;
    }

    public void deleteCategory(Integer categoryId)
    {
        CategoryEntity category = validator.getCategoryEntityFromId(categoryId);
        if (category.getActive())
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_IN_USE));
        }
        List<MenuEntity> menu = menuDAO.findByCategory(category);
        if (!menu.isEmpty())
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_IN_USE));
        }
        categoryDAO.delete(category);
    }

    private void validateInputForCreateDTO(CategoryCreateDTO createDTO)
    {
        validateType(createDTO.getType());
        validateSubType(createDTO.getType(), createDTO.getSubType());
        checkIfDuplicateEntryExists(createDTO);
        checkIfDisplayRankAlreadyUsed(createDTO.getDisplayRank());
    }

    private void validateUpdateDTO(Integer categoryId, CategoryUpdateDTO updateDTO)
    {
        CategoryEntity categoryEntity = validator.getCategoryEntityFromId(categoryId);
        if (categoryEntity.equals(mapper.map(updateDTO, CategoryEntity.class)))
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.NO_FIELDS_UPDATED));
        }
        validateType(updateDTO.getType());
        validateSubType(updateDTO.getType(), updateDTO.getSubType());
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

    private void checkIfDuplicateEntryExists(CategoryCreateDTO createDTO)
    {
        CategoryEntity category = categoryDAO.findByNameAndTypeAndSubType(createDTO.getName(), createDTO.getType(), createDTO.getSubType());
        if (category != null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.CATEGORY_ALREADY_EXISTS));
        }
    }

    private void checkIfDisplayRankAlreadyUsed(Integer displayOrder)
    {
        CategoryEntity category = categoryDAO.findByDisplayRank(displayOrder);
        if (category != null)
        {
            throw new ServerException(new ErrorMessage(ErrorCodes.INVALID_CATEGORY_DISPLAY_RANK));
        }
    }
}
