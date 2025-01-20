package net.pfsnc.apicomp.fh.controller.rest.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.pfsnc.apicomp.fh.dto.CourseDTO;
import net.pfsnc.apicomp.fh.dto.EnrollmentDTO;
import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.dto.TeacherDTO;
import net.pfsnc.apicomp.fh.service.CourseService;
import net.pfsnc.apicomp.fh.service.EnrollmentService;
import net.pfsnc.apicomp.fh.service.StudentService;
import net.pfsnc.apicomp.fh.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/batch")
public class BatchRequestController {

    private final Logger LOGGER = Logger.getLogger(BatchRequestController.class.getName());

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public BatchRequestController(StudentService studentService, TeacherService teacherService, CourseService courseService, EnrollmentService enrollmentService, ObjectMapper objectMapper) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public List<BatchResponse> handleBatchRequests(@RequestBody BatchRequest batchRequest) {
        LOGGER.info("Handling batch requests");
        List<BatchResponse> responses = new ArrayList<>();

        for (BatchRequest.Request request : batchRequest.getRequests()) {
            Map<String, Integer> params = request.getParams() != null ? request.getParams() : new HashMap<>();

            int page = params.getOrDefault("page", 0);
            int size = params.getOrDefault("size", 20);
            Pageable pageable = PageRequest.of(page, size);
            try {
                switch (request.getMethod()) {
                    case "GET":
                        if (request.getEndpoint().equals("/students")) {
                            LOGGER.info("Getting all students");
                            responses.add(new BatchResponse(request, ResponseEntity.ok(studentService.findAll())));
                        } else if (request.getEndpoint().startsWith("/students/")) {
                            LOGGER.info("Getting student by id");
                            Long id = Long.parseLong(request.getEndpoint().split("/")[2]);
                            responses.add(new BatchResponse(request, ResponseEntity.ok(studentService.findById(id))));
                        } else if (request.getEndpoint().equals("/teachers")) {
                            LOGGER.info("Getting all teachers");
                            responses.add(new BatchResponse(request, ResponseEntity.ok(teacherService.findAll())));
                        } else if (request.getEndpoint().startsWith("/teachers/")) {
                            LOGGER.info("Getting teacher by id");
                            Long id = Long.parseLong(request.getEndpoint().split("/")[2]);
                            responses.add(new BatchResponse(request, ResponseEntity.ok(teacherService.findById(id))));
                        } else if (request.getEndpoint().equals("/courses")) {
                            LOGGER.info("Getting all courses");
                            responses.add(new BatchResponse(request, ResponseEntity.ok(courseService.findAll(pageable))));
                        } else if (request.getEndpoint().startsWith("/courses/")) {
                            LOGGER.info("Getting course by id");
                            Long id = Long.parseLong(request.getEndpoint().split("/")[2]);
                            responses.add(new BatchResponse(request, ResponseEntity.ok(courseService.findById(id))));
                        } else if (request.getEndpoint().equals("/enrollments")) {
                            LOGGER.info("Getting all enrollments");
                            responses.add(new BatchResponse(request, ResponseEntity.ok(enrollmentService.findAll(pageable))));
                        } else if (request.getEndpoint().startsWith("/enrollments/")) {
                            LOGGER.info("Getting enrollment by id");
                            Long id = Long.parseLong(request.getEndpoint().split("/")[2]);
                            responses.add(new BatchResponse(request, ResponseEntity.ok(enrollmentService.findById(id))));
                        }
                        break;
                    case "POST":
                        switch (request.getEndpoint()) {
                            case "/students" -> {
                                LOGGER.info("Creating student");
                                StudentDTO student = objectMapper.convertValue(request.getBody(), StudentDTO.class);
                                responses.add(new BatchResponse(request, ResponseEntity.ok(studentService.create(student))));
                            }
                            case "/teachers" -> {
                                LOGGER.info("Creating teacher");
                                TeacherDTO teacher = objectMapper.convertValue(request.getBody(), TeacherDTO.class);
                                responses.add(new BatchResponse(request, ResponseEntity.ok(teacherService.save(teacher))));
                            }
                            case "/courses" -> {
                                LOGGER.info("Creating course");
                                CourseDTO course = objectMapper.convertValue(request.getBody(), CourseDTO.class);
                                responses.add(new BatchResponse(request, ResponseEntity.ok(courseService.save(course))));
                            }
                            case "/enrollments" -> {
                                EnrollmentDTO enrollment = objectMapper.convertValue(request.getBody(), EnrollmentDTO.class);
                                responses.add(new BatchResponse(request, ResponseEntity.ok(enrollmentService.save(enrollment))));
                            }
                        }
                        break;
                    default:
                        LOGGER.warning("Unsupported method");
                        responses.add(new BatchResponse(request, ResponseEntity.badRequest().body("Unsupported method")));
                }
            } catch (Exception e) {
                responses.add(new BatchResponse(request, ResponseEntity.status(500).body(e.getMessage())));
            }
        }

        return responses;
    }
}
