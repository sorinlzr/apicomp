package net.pfsnc.apicomp.fh.mapper;

import net.pfsnc.apicomp.fh.dto.TeacherDTO;
import net.pfsnc.apicomp.fh.model.Teacher;

import java.util.stream.Collectors;

public class TeacherMapper {
    public static TeacherDTO toTeacherDTO(Teacher teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setName(teacher.getName());
        teacherDTO.setDepartment(teacher.getDepartment());
        if(teacher.getCourses() != null) {
            teacherDTO.setCourses(teacher.getCourses()
                    .stream()
                    .map(CourseMapper::toCourseDTO)
                    .collect(Collectors.toList()));
        }
        return teacherDTO;
    }
}
