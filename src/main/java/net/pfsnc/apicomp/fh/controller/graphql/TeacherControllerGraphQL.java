package net.pfsnc.apicomp.fh.controller.graphql;

import net.pfsnc.apicomp.fh.model.Teacher;
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
    public List<Teacher> teachers(@Argument Integer limit, @Argument Integer offset) {
        int actualLimit = (limit != null) ? limit : 10;
        int actualOffset = (offset != null) ? offset : 0;
        return teacherService.findAllWithPagination(actualLimit, actualOffset);
    }

    @QueryMapping
    public Teacher teacher(@Argument Long id) {
        return teacherService.findById(id).orElse(null);
    }

    @MutationMapping
    public Teacher createTeacher(@Argument String name, @Argument String department) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setDepartment(department);
        return teacherService.save(teacher);
    }

    @MutationMapping
    public Teacher updateTeacher(@Argument Long id, @Argument String name, @Argument String department) {
        return teacherService.findById(id).map(teacher -> {
            teacher.setName(name);
            teacher.setDepartment(department);
            return teacherService.save(teacher);
        }).orElse(null);
    }
}
