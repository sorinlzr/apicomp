package net.pfsnc.apicomp.fh.controller.graphql;

import net.pfsnc.apicomp.fh.dto.TeacherDTO;
import net.pfsnc.apicomp.fh.service.TeacherService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TeacherControllerGraphQL {
    private final TeacherService teacherService;

    public TeacherControllerGraphQL(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @QueryMapping
    public List<TeacherDTO> teachers(@Argument Integer limit, @Argument Integer offset) {
        int actualLimit = (limit != null) ? limit : 10;
        int actualOffset = (offset != null) ? offset : 0;
        return teacherService.findAllWithPagination(actualLimit, actualOffset);
    }

    @QueryMapping
    public TeacherDTO teacher(@Argument Long id) {
        return teacherService.findById(id);
    }

    @MutationMapping
    public TeacherDTO createTeacher(@Argument String name, @Argument String department) {
        TeacherDTO teacher = new TeacherDTO();
        teacher.setName(name);
        teacher.setDepartment(department);
        return teacherService.save(teacher);
    }

    @MutationMapping
    public TeacherDTO updateTeacher(@Argument Long id, @Argument String name, @Argument String department) {
        TeacherDTO teacherDTO = teacherService.findById(id);
        if (teacherDTO == null) {
            return null;
        }
        teacherDTO.setName(name);
        teacherDTO.setDepartment(department);
        return teacherService.save(teacherDTO);
    }
}
