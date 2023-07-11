package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testCreateUserWithOneRole(){

        Role role = entityManager.find(Role.class,1);
        User user = new User( "harsha@gmail.com","harsha2023","Harsha","Kanwar");
        user.addRole(role);

        User savedUser = repo.save(user);
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateUserWithTwoRoles(){

        User userGaje = new User( "gaje@gmail.com","gaje2023","Gajesingh","Bhati");
        userGaje.addRole(new Role(3));
        userGaje.addRole(new Role(5));

        User savedUser = repo.save(userGaje);
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){

        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));

    }
    @Test
    public void testGetUserById(){

        User user = repo.findById(1).get();
        System.out.println(user);
        Assertions.assertThat(user).isNotNull();
    }
    @Test
    public void testUpdateUserDetails(){

        User user = repo.findById(1).get();
        user.setEnabled(true);
        user.setEmail("programmer.gmail.com");

        repo.save(user);
    }

    @Test
    public void testUpdateUserRoles(){

        User userGaje = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        userGaje.getRoles().remove(roleEditor);
        userGaje.addRole(roleSalesperson);
        repo.save(userGaje);
    }

    @Test
    public void testDeleteUser(){

        Integer userId = 2;
        repo.deleteById(userId);
    }
}
