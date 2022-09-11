package com.company.module_test_4.service;

import com.company.module_test_4.entity.Sheet;
import com.company.module_test_4.entity.Teacher;
import io.jmix.core.FetchPlan;

import java.util.List;
import java.util.UUID;

public interface TeacherService {
    Sheet getSheetByNo(Long sheetNo);

    List<Sheet> getSheetsByTeacher(UUID teacherID);

    List<Teacher> getTeachersByLastName(String lastName);

    Teacher getTeacherById(UUID teacherID, FetchPlan fetchPlan);
}
