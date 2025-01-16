package net.pfsnc.apicomp.fh.dto;

import lombok.Data;
import java.util.Date;

@Data
public class EnrollmentDTO {
    private Long id;
    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Date enrollmentDate;
}