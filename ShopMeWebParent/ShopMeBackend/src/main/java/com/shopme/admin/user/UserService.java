package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public void save(User user) {
        boolean isUpdatingUser = (user.getId() != null);
        if (isUpdatingUser) {
            User existingUser = userRepository.findById(user.getId()).get();

            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        userRepository.save(user);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getByEmail(email);

        if (userByEmail == null) return true;

        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            return false;
        } else if (userByEmail.getId() != id) {
            return false;
        }
        return true;
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public User getById(Integer id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException nex) {
            throw new UserNotFoundException("Could not find user with ID " + id);
        }
    }

    public void deleteUser(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find user with ID " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepository.updateEnabledStatus(id, enabled);
    }
}
