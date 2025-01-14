package net.pfsnc.apicomp.fh.controller.graphql;

import net.pfsnc.apicomp.fh.model.Course;
import net.pfsnc.apicomp.fh.service.CourseService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CourseControllerGraphQL {
    private final CourseService courseService;

    public CourseControllerGraphQL(CourseService courseService) {
        this.courseService = courseService;
    }

    @QueryMapping
    public List<Course> courses(@Argument Integer limit, @Argument Integer offset) {
        int actualLimit = (limit != null) ? limit : 20;
        int actualOffset = (offset != null) ? offset : 0;
        return courseService.findAllWithPagination(actualLimit, actualOffset);
    }

    @QueryMapping
    public Course course(@Argument Long id) {
        return courseService.findById(id).orElse(null);
    }

    @MutationMapping
    public Course createCourse(@Argument String title, @Argument String description) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        return courseService.save(course);
    }

    @MutationMapping
    public Course updateCourse(@Argument Long id, @Argument String title, @Argument String description) {
        return courseService.findById(id).map(course -> {
            course.setTitle(title);
            course.setDescription(description);
            return courseService.save(course);
        }).orElse(null);
    }
}
