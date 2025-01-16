package net.pfsnc.apicomp.fh.dto;

import lombok.Data;
import java.util.List;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private List<EnrollmentDTO> enrollments;
}
