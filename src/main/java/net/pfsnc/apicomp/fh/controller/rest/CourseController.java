package net.pfsnc.apicomp.fh.controller.rest;

import net.pfsnc.apicomp.fh.dto.CourseDTO;
import net.pfsnc.apicomp.fh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final Logger LOGGER = Logger.getLogger(CourseController.class.getName());

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> getAllCourses(Pageable pageable) {
        LOGGER.info("Getting all courses");
        return courseService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable Long id) {
        LOGGER.info("Getting course by id: " + id);
        return courseService.findById(id);
    }

    @PostMapping
    public CourseDTO createCourse(@RequestBody CourseDTO course) {
        LOGGER.info("Creating course: " + course);
        return courseService.save(course);
    }
}
