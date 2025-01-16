package net.pfsnc.apicomp.fh.controller.graphql;

import net.pfsnc.apicomp.fh.dto.CourseDTO;
import net.pfsnc.apicomp.fh.dto.EnrollmentDTO;
import net.pfsnc.apicomp.fh.service.CourseService;
import net.pfsnc.apicomp.fh.service.EnrollmentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CourseControllerGraphQL {
    private final CourseService courseService;

    private final EnrollmentService enrollmentService;

    public CourseControllerGraphQL(CourseService courseService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @QueryMapping
    public List<CourseDTO> courses(@Argument Integer limit, @Argument Integer offset) {
        int actualLimit = (limit != null) ? limit : 20;
        int actualOffset = (offset != null) ? offset : 0;
        return courseService.findAllWithPagination(actualLimit, actualOffset);
    }

    @QueryMapping
    public CourseDTO course(@Argument Long id) {
        return courseService.findById(id);
    }

    @MutationMapping
    public CourseDTO createCourse(@Argument String title, @Argument String description) {
        CourseDTO course = new CourseDTO();
        course.setTitle(title);
        course.setDescription(description);
        return courseService.save(course);
    }

    @MutationMapping
    public CourseDTO updateCourse(@Argument Long id, @Argument String title, @Argument String description) {
        CourseDTO courseDTO = courseService.findById(id);
        if (courseDTO == null) {
            return null;
        }
        courseDTO.setTitle(title);
        courseDTO.setDescription(description);
        return courseService.save(courseDTO);
    }

    @SchemaMapping(typeName = "Course", field = "enrollments")
    public List<EnrollmentDTO> getEnrollments(CourseDTO course) {
        return enrollmentService.findByCourseId(course.getId());
    }
}
