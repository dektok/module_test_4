package com.company.module_test_4;

import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseTest {

    @Autowired
    SystemAuthenticator authenticator;

    @BeforeEach
    void setUp() {
        authenticator.begin("admin");
    }

    @AfterEach
    void tearDown() {
        authenticator.end();  }

}
