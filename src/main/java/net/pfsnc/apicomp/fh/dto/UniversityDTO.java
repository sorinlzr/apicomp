package net.pfsnc.apicomp.fh.dto;

import lombok.Data;
import java.util.List;

@Data
public class UniversityDTO {
    private Long id;
    private String name;
    private List<StudentDTO> students;
    private List<TeacherDTO> teachers;
    private List<EnrollmentDTO> enrollments;
}

