package net.pfsnc.apicomp.fh.controller.rest;

import net.pfsnc.apicomp.fh.dto.TeacherDTO;
import net.pfsnc.apicomp.fh.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final Logger LOGGER = Logger.getLogger(TeacherController.class.getName());

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherDTO> getAllTeachers() {
        LOGGER.info("Getting all teachers");
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public TeacherDTO getTeacherById(@PathVariable Long id) {
        LOGGER.info("Getting teacher by id: " + id);
        return teacherService.findById(id);
    }

    @PostMapping
    public TeacherDTO createTeacher(@RequestBody TeacherDTO teacher) {
        LOGGER.info("Creating teacher: " + teacher);
        return teacherService.save(teacher);
    }
}
