package net.pfsnc.apicomp.fh.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class TeacherDTO {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Department is mandatory")
    private String department;
    private List<CourseDTO> courses;
}