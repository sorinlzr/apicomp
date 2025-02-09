package net.pfsnc.apicomp.fh.controller.rest;

import jakarta.validation.Valid;
import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final Logger LOGGER = Logger.getLogger(StudentController.class.getName());

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        LOGGER.info("Getting all students");
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id) {
        LOGGER.info("Getting student by id: " + id);
        return studentService.findById(id);
    }

    @PostMapping
    public StudentDTO createStudent(@Valid @RequestBody StudentDTO student) {
        LOGGER.info("Creating student: " + student);
        return studentService.create(student);
    }

    @PutMapping("/{id}")
    public StudentDTO updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO student) {
        LOGGER.info("Updating student with id: " + id);
        student.setId(id);
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        LOGGER.info("Deleting student by id: " + id);
        studentService.deleteById(id);
    }
}
