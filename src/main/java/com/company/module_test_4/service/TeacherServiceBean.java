package com.company.module_test_4.service;

import com.company.module_test_4.entity.Sheet;
import com.company.module_test_4.entity.Teacher;
import com.company.module_test_4.repository.TeacherRepository;
import io.jmix.core.FetchPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.UUID;

import static io.jmix.core.ValueLoadContext.createQuery;

@Service
public class TeacherServiceBean implements TeacherService{

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public Sheet getSheetByNo(Long sheetNo){

        return (Sheet)entityManager.createQuery("SELECT s from Sheet s where s.sheetNo = ?1")
                .setParameter(1, sheetNo)
                .getSingleResult();
    }

    @Override
    public List<Sheet> getSheetsByTeacher(UUID teacherID) {
        return (List<Sheet>) entityManager
                .createQuery("SELECT s from Sheet s " +
                        "join fetch s.teacher where s.teacher.id = ?1")
                .setParameter(1, teacherID)
                .getResultList();
    }

    @Override
    public List<Teacher> getTeachersByLastName(String lastName) {

        return teacherRepository.findAllByUserLastName(lastName);
    }

    @Override
    public Teacher getTeacherById(UUID teacherID, FetchPlan fetchPlan) {

        return teacherRepository.findById(teacherID, fetchPlan).stream().findFirst().orElseGet(() ->  null);
    }

}
