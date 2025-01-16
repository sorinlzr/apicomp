package net.pfsnc.apicomp.fh.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeacherDTO {
    private Long id;
    private String name;
    private String department;
    private List<CourseDTO> courses;
}