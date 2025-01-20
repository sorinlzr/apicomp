package net.pfsnc.apicomp.fh.controller.graphql;

import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.exception.ValidationException;
import net.pfsnc.apicomp.fh.service.StudentService;
import org.reactivestreams.Publisher;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class StudentControllerGraphQL {
    private final Logger LOGGER = Logger.getLogger(StudentControllerGraphQL.class.getName());
    private final StudentService studentService;

    public StudentControllerGraphQL(StudentService studentService) {
        this.studentService = studentService;
    }

    @QueryMapping
    public List<StudentDTO> students() {
        LOGGER.info("Getting all students");
        return studentService.findAll();
    }

    @QueryMapping
    public StudentDTO student(@Argument Long id) {
        LOGGER.info("Getting student by id: " + id);
        return studentService.findById(id);
    }

    @MutationMapping
    public StudentDTO createStudent(@Argument String name, @Argument String email) {
        LOGGER.info("Creating student with name: " + name + " and email: " + email);
        validateStudent(name, email);
        StudentDTO student = new StudentDTO();
        student.setName(name);
        student.setEmail(email);

        return studentService.create(student);
    }

    @MutationMapping
    public StudentDTO updateStudent(@Argument Long id, @Argument String name, @Argument String email) {
        LOGGER.info("Updating student with id: " + id + " to name: " + name + " and email: " + email);
        validateStudent(name, email);
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(id);
        studentDTO.setName(name);
        studentDTO.setEmail(email);
        return studentService.update(studentDTO);
    }

    @MutationMapping
    public void deleteStudent(@Argument Long id) {
        LOGGER.info("Deleting student by id: " + id);
        studentService.deleteById(id);
    }

    @SubscriptionMapping
    public Publisher<Long> studentCount() {
        LOGGER.info("Subscribing to student count");
        return studentService.getStudentCountPublisher();
    }

    private void validateStudent(String name, String email) {
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Name is mandatory");
        }
        if (email == null || email.isEmpty()) {
            throw new ValidationException("Email is mandatory");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Email should be valid");
        }
    }
}

