package net.pfsnc.apicomp.fh.controller.graphql;

import net.pfsnc.apicomp.fh.dto.StudentDTO;
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
    public List<StudentDTO> students(@Argument Integer limit, @Argument Integer offset) {
        System.out.println("Fetching students with limit: " + limit + ", offset: " + offset);

        int actualLimit = (limit != null) ? limit : 10;
        int actualOffset = (offset != null) ? offset : 0;
        return studentService.findAllWithPagination(actualLimit, actualOffset);
    }

    @QueryMapping
    public StudentDTO student(@Argument Long id) {
        return studentService.findById(id);
    }

    @MutationMapping
    public StudentDTO createStudent(@Argument String name, @Argument String email) {
        StudentDTO student = new StudentDTO();
        student.setName(name);
        student.setEmail(email);
        return studentService.save(student);
    }

    @MutationMapping
    public StudentDTO updateStudent(@Argument Long id, @Argument String name, @Argument String email) {
        StudentDTO studentDTO = studentService.findById(id);
        if (studentDTO == null) {
            return null;
        }
        studentDTO.setName(name);
        studentDTO.setEmail(email);
        return studentService.save(studentDTO);
    }
}

