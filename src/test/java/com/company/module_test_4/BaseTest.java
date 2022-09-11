package com.company.module_test_4;

import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BaseTest {

    @Autowired
    protected UnconstrainedDataManager uDataManager;

    @Autowired
    SystemAuthenticator authenticator;

    protected List<Object> entitiesToRemove = new ArrayList<>();

    @BeforeEach
    void setUp() {
        authenticator.begin("admin");
    }

    @AfterEach
    void tearDown() {
        authenticator.end();
        uDataManager.remove(entitiesToRemove);
    }

}
