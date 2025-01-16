package net.pfsnc.apicomp.fh.dto;

import lombok.Data;
import java.util.List;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private String teacherName;
    private List<EnrollmentDTO> enrollments;
}