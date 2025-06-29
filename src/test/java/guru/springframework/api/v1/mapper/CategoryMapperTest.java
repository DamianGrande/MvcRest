package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryMapperTest {

    public static final String NAME = "Joe";
    public static final long ID = 1L;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTO() {

        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, category.getName());

    }

}