package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @MockBean private CategoryRepository repository;
    @InjectMocks private CategoryService service;

    @Test
    public void checkUniqueInNewModeReturnDuplicateName(){
        Integer id = null;
        String name = "Computers";
        String alias = "ANC";

        Category category = new Category(id,name,alias);

        Mockito.when(repository.findByName(name)).thenReturn(category);
        Mockito.when(repository.findByAlias(alias)).thenReturn(category);
        String result = service.checkUnique(id, name, alias);

        Assertions.assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void checkUniqueInNewModeReturnDuplicateAlias(){
        Integer id = null;
        String name = "NameABC";
        String alias = "computers";

        Category category = new Category(id,name,alias);

        Mockito.when(repository.findByName(name)).thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(category);
        String result = service.checkUnique(id, name, alias);

        Assertions.assertThat(result).isEqualTo("DuplicateAlias");
    }

    @Test
    public void checkUniqueInNewModeReturnOK(){
        Integer id = null;
        String name = "NameABC";
        String alias = "computers";

        Category category = new Category(id,name,alias);

        Mockito.when(repository.findByName(name)).thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);

        Assertions.assertThat(result).isEqualTo("OK");
    }

    @Test
    public void checkUniqueInEditModeReturnDuplicateName(){
        Integer id = 1;
        String name = "Computers";
        String alias = "ANC";

        Category category = new Category(2,name,alias);

        Mockito.when(repository.findByName(name)).thenReturn(category);
        Mockito.when(repository.findByAlias(alias)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);

        Assertions.assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void checkUniqueInEditModeReturnDuplicateAlias(){
        Integer id = 1;
        String name = "NameABC";
        String alias = "computers";

        Category category = new Category(2,name,alias);

        Mockito.when(repository.findByName(name)).thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(category);
        String result = service.checkUnique(id, name, alias);

        Assertions.assertThat(result).isEqualTo("DuplicateAlias");
    }

    @Test
    public void checkUniqueInEditModeReturnOK(){
        Integer id = 1;
        String name = "NameABC";
        String alias = "computers";

        Category category = new Category(id,name,alias);

        Mockito.when(repository.findByName(name)).thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);

        Assertions.assertThat(result).isEqualTo("OK");
    }
}
