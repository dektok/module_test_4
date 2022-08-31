package com.company.module_test_4;

import com.company.module_test_4.entity.User;
import com.company.module_test_4.security.DatabaseUserRepository;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestSystemUser {

    @Autowired
    SystemAuthenticator authenticator;

    @Autowired
    CurrentAuthentication currentAuthentication;

    @Autowired
    DatabaseUserRepository userRepository;

    @BeforeEach
    void setUp() {
        authenticator.begin();
    }

    @AfterEach
    void tearDown() {
        authenticator.end();  }

    @Test
    void isAdmin() {

        authenticator. runWithUser("admin",() -> {
            UserDetails user = currentAuthentication.getUser();
            String username = user.getUsername();
            User userObject = userRepository.loadUserByUsername(username);

            assertEquals( userObject.getUsername(), username );

        });
    }

}
