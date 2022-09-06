package com.company.module_test_4;

import com.company.module_test_4.entity.User;
import com.company.module_test_4.security.DatabaseUserRepository;
import io.jmix.core.security.CurrentAuthentication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestSystemUser extends BaseTest {

    @Autowired
    CurrentAuthentication currentAuthentication;

    @Autowired
    DatabaseUserRepository userRepository;

    @Test
    void isAdmin() {

        UserDetails user = currentAuthentication.getUser();
        String username = user.getUsername();
        User userObject = userRepository.loadUserByUsername(username);

        assertEquals( userObject.getUsername(), username );

    }

}
