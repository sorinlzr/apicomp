package net.pfsnc.apicomp.fh.mapper;

import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.model.Student;

import java.util.stream.Collectors;

public class StudentMapper {

    public static StudentDTO toStudentDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        if (student.getEnrollments() != null) {
            dto.setEnrollments(student.getEnrollments()
                    .stream()
                    .map(EnrollmentMapper::toEntrollmentDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
