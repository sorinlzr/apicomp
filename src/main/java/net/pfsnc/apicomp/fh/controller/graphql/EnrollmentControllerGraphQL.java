package net.pfsnc.apicomp.fh.controller.graphql;

import net.pfsnc.apicomp.fh.dto.EnrollmentDTO;
import net.pfsnc.apicomp.fh.service.EnrollmentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EnrollmentControllerGraphQL {
    private final EnrollmentService enrollmentService;

    public EnrollmentControllerGraphQL(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @QueryMapping
    public EnrollmentDTO enrollment(@Argument Long id) {
        return enrollmentService.findById(id);
    }

    @QueryMapping
    public List<EnrollmentDTO> enrollmentsForCourse(@Argument Long courseId) {
        return enrollmentService.findByCourseId(courseId);
    }

    @QueryMapping
    public List<EnrollmentDTO> enrollmentsForStudent(@Argument Long studentId) {
        return enrollmentService.findByStudentId(studentId);
    }

    @MutationMapping
    public EnrollmentDTO createEnrollment(@Argument Long courseId, @Argument Long studentId, @Argument String enrollmentDate) {
        EnrollmentDTO enrollment = new EnrollmentDTO();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        if (enrollmentDate != null) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(enrollmentDate);
                enrollment.setEnrollmentDate(date);
            } catch (ParseException e) {
                enrollment.setEnrollmentDate(new Date());
            }
        }

        return enrollmentService.save(enrollment);
    }
}
