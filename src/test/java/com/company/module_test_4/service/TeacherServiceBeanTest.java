package com.company.module_test_4.service;

import com.company.module_test_4.BaseTest;
import com.company.module_test_4.entity.Sheet;
import com.company.module_test_4.entity.Teacher;
import com.company.module_test_4.entity.User;
import com.company.module_test_4.repository.TeacherRepository;

import io.jmix.core.FetchPlans;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.security.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

class TeacherServiceBeanTest extends BaseTest {


    @Autowired
    TeacherServiceBean teacherServiceBean;

    @Autowired
    CurrentAuthentication currentAuthentication;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FetchPlans fetchPlans;

    @Test
    void getSheetByNo() {

        UserDetails user = currentAuthentication.getUser();

        Teacher teacher = uDataManager.create(Teacher.class);
        teacher.setUser((User) user);

        Long sheetNo = 1L;

        Sheet sheet = uDataManager.create(Sheet.class);
        sheet.setSheetNo(sheetNo);
        sheet.setTeacher(teacher);

        entitiesToRemove.add(sheet);
        entitiesToRemove.add(teacher);

        uDataManager.save(teacher, sheet);

        Sheet retSheet = teacherServiceBean.getSheetByNo(sheetNo);

        Assertions.assertEquals(sheet.getId(),
                retSheet.getId());

    }

    @Test
    void getSheetsByTeacher() {

        UserDetails user = currentAuthentication.getUser();

        Teacher teacher = uDataManager.create(Teacher.class);

        teacher.setUser((User) user);

        Long sheetNo = 1L;

        Sheet sheet1 = uDataManager.create(Sheet.class);
        sheet1.setSheetNo(sheetNo);
        sheet1.setTeacher(teacher);

        Long sheetNo2 = 2L;

        Sheet sheet2 = uDataManager.create(Sheet.class);
        sheet2.setSheetNo(sheetNo2);
        sheet2.setTeacher(teacher);

        entitiesToRemove.add(sheet1);
        entitiesToRemove.add(sheet2);
        entitiesToRemove.add(teacher);

        uDataManager.save(teacher, sheet1, sheet2);

        long count = teacherServiceBean.getSheetsByTeacher(teacher.getId())
                .stream()
                .filter(sheet -> sheet.getTeacher().getId().equals(teacher.getId()))
                .count();

        // должно вернуться 2 записи с такими ID
        Assertions.assertEquals(2,
                count);


    }

    @Test
    void getTeachersByLastName() {

        User user;
        String userLastName;

        try {
            user = (User)userRepository.loadUserByUsername("TestTest");
            userLastName = user.getLastName();

        } catch (UsernameNotFoundException e) {
            user = uDataManager.create(User.class);
            userLastName = "Иванов";
            user.setFirstName("Иван");
            user.setLastName(userLastName);
            user.setUsername("TestTest");
            user.setPassword("1");

            uDataManager.save(user);
        }

        Teacher teacher = uDataManager.create(Teacher.class);

        teacher.setUser(user);

        entitiesToRemove.add(teacher);

        uDataManager.save(teacher);

        var found = teacherServiceBean.getTeachersByLastName(userLastName);

        Assertions.assertEquals(1, found.size());
        Assertions.assertTrue(found.contains(teacher));
    }

    @Test
    void getTeacherById() {

        User user;

        try {
            user = (User)userRepository.loadUserByUsername("TestTest");

        } catch (UsernameNotFoundException e) {
            user = uDataManager.create(User.class);
            user.setFirstName("Иван");
            user.setLastName("Иванов");
            user.setUsername("TestTest");
            user.setPassword("1");

            uDataManager.save(user);
        }

        Teacher teacher = uDataManager.create(Teacher.class);

        teacher.setUser(user);

        entitiesToRemove.add(teacher);

        uDataManager.save(teacher);

        Teacher foundTeacher = teacherServiceBean.getTeacherById(teacher.getId(),
            fetchPlans.builder(Teacher.class)
                .add("user.lastName")
                .add("user.firstName")
                .build());

        Assertions.assertNotNull(foundTeacher);
        Assertions.assertEquals(teacher.getId(), foundTeacher.getId());

        Assertions.assertDoesNotThrow( () -> {
            var lastName = foundTeacher.getUser().getLastName();
            var firstName = foundTeacher.getUser().getFirstName();
        });

        Assertions.assertThrows(Exception.class, () -> {
            var email = foundTeacher.getUser().getEmail();
        });

    }
}