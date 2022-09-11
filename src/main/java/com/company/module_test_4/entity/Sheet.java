package com.company.module_test_4.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import liquibase.repackaged.net.sf.jsqlparser.statement.select.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "SHEET", indexes = {
        @Index(name = "IDX_SHEET_TEACHER", columnList = "TEACHER_ID")
})
@Entity
public class Sheet {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "SHEET_NO", nullable = false)
    @NotNull
    private Long sheetNo;

    @JoinColumn(name = "TEACHER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(Long sheetNo) {
        this.sheetNo = sheetNo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}