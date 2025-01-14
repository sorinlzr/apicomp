package net.pfsnc.apicomp.fh.controller.graphql;

import net.pfsnc.apicomp.fh.model.Student;
import net.pfsnc.apicomp.fh.service.StudentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StudentControllerGraphQL {
    private final StudentService studentService;

    public StudentControllerGraphQL(StudentService studentService) {
        this.studentService = studentService;
    }

    @QueryMapping
    public List<Student> students(@Argument Integer limit, @Argument Integer offset) {
        System.out.println("Fetching students with limit: " + limit + ", offset: " + offset);

        int actualLimit = (limit != null) ? limit : 10;
        int actualOffset = (offset != null) ? offset : 0;
        return studentService.findAllWithPagination(actualLimit, actualOffset);
    }

    @QueryMapping
    public Student student(@Argument Long id) {
        return studentService.findById(id).orElse(null);
    }

    @MutationMapping
    public Student createStudent(@Argument String name, @Argument String email) {
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        return studentService.save(student);
    }

    @MutationMapping
    public Student updateStudent(@Argument Long id, @Argument String name, @Argument String email) {
        return studentService.findById(id).map(student -> {
            student.setName(name);
            student.setEmail(email);
            return studentService.save(student);
        }).orElse(null);
    }
}

