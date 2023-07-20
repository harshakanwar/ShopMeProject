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

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {

        Role role = entityManager.find(Role.class, 5);
        User user = new User("test@gmail.com", "test023", "Test", "User");
        user.addRole(role);

        User savedUser = repo.save(user);
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles() {

        User userGaje = new User("pallu@gmail.com", "pallu2023", "Priyanka", "Kanwar");
        userGaje.addRole(new Role(2));
        userGaje.addRole(new Role(4));

        User savedUser = repo.save(userGaje);
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {

        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));

    }

    @Test
    public void testGetUserById() {

        User user = repo.findById(1).get();
        System.out.println(user);
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {

        User user = repo.findById(1).get();
        user.setEnabled(true);
        user.setEmail("programmer.gmail.com");

        repo.save(user);
    }

    @Test
    public void testUpdateUserRoles() {

        User userGaje = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        userGaje.getRoles().remove(roleEditor);
        userGaje.addRole(roleSalesperson);
        repo.save(userGaje);
    }

    @Test
    public void testDeleteUser() {

        Integer userId = 2;
        repo.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail() {

        String email = "harsha@gmail.com";
        User user = repo.getByEmail(email);
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 1;
        Long countById = repo.countById(id);

        Assertions.assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void disableUserById() {
        Integer id = 19;
        repo.updateEnabledStatus(id, true);

    }
}
