package net.pfsnc.apicomp.fh.mapper;

import net.pfsnc.apicomp.fh.dto.EnrollmentDTO;
import net.pfsnc.apicomp.fh.model.Enrollment;

public class EnrollmentMapper {

    public static EnrollmentDTO toEntrollmentDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setCourseId(enrollment.getCourse().getId());
        dto.setCourseTitle(enrollment.getCourse().getTitle());
        dto.setCourseDescription(enrollment.getCourse().getDescription());
        dto.setStudentId(enrollment.getStudent().getId());
        dto.setStudentName(enrollment.getStudent().getName());
        dto.setStudentEmail(enrollment.getStudent().getEmail());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        return dto;
    }
}
