package com.company.module_test_4.repository;

import com.company.module_test_4.entity.Teacher;
import io.jmix.core.repository.FetchPlan;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository  extends JmixDataRepository<Teacher, UUID> {

    List<Teacher> findAllByUserLastName(String lastName);

    List<Teacher> findById(UUID id, FetchPlan fetchPlan);
}
