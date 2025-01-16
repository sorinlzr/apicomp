package net.pfsnc.apicomp.fh.controller.rest;

import net.pfsnc.apicomp.fh.dto.CourseDTO;
import net.pfsnc.apicomp.fh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> getAllCourses(Pageable pageable) {
        return courseService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    public CourseDTO createCourse(@RequestBody CourseDTO course) {
        return courseService.save(course);
    }
}
