package net.pfsnc.apicomp.fh.mapper;

import net.pfsnc.apicomp.fh.dto.CourseDTO;
import net.pfsnc.apicomp.fh.model.Course;

import java.util.stream.Collectors;

public class CourseMapper {
    public static CourseDTO toCourseDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setTeacherId(course.getTeacher().getId());
        dto.setTeacherName(course.getTeacher().getName());
        if (course.getEnrollments() != null) {
            dto.setEnrollments(course.getEnrollments()
                    .stream()
                    .map(EnrollmentMapper::toEntrollmentDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
