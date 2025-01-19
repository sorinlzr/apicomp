package net.pfsnc.apicomp.fh.grpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.stub.StreamObserver;
import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.dto.TeacherDTO;
import net.pfsnc.apicomp.fh.service.CourseService;
import net.pfsnc.apicomp.fh.service.EnrollmentService;
import net.pfsnc.apicomp.fh.service.StudentService;
import net.pfsnc.apicomp.fh.service.TeacherService;
import org.springframework.stereotype.Service;

@Service
public class BatchGrpcService extends BatchServiceGrpc.BatchServiceImplBase {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final ObjectMapper objectMapper;

    public BatchGrpcService(StudentService studentService, TeacherService teacherService, CourseService courseService, EnrollmentService enrollmentService, ObjectMapper objectMapper) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.objectMapper = objectMapper;
    }
    @Override
    public void processBatch(BatchRequest request, StreamObserver<SingleResponse> responseObserver) {
        for (SingleRequest singleRequest : request.getRequestsList()) {
            SingleResponse.Builder singleResponseBuilder = SingleResponse.newBuilder()
                    .setEndpoint(singleRequest.getEndpoint());

            try {
                switch (singleRequest.getMethod()) {
                    case "GET" -> handleGet(singleRequest, singleResponseBuilder);
                    case "POST" -> handlePost(singleRequest, singleResponseBuilder);
                    default -> singleResponseBuilder
                            .setStatus("400 Bad Request")
                            .setBody("Unsupported method: " + singleRequest.getMethod());
                }
            } catch (Exception e) {
                singleResponseBuilder
                        .setStatus("500 Internal Server Error")
                        .setBody(e.getMessage());
            }

            responseObserver.onNext(singleResponseBuilder.build());
        }

        responseObserver.onCompleted();
    }

    private void handleGet(SingleRequest request, SingleResponse.Builder responseBuilder) throws JsonProcessingException {
        String endpoint = request.getEndpoint();
        if (endpoint.equals("/students")) {
            responseBuilder
                    .setStatus("200 OK")
                    .setBody(objectMapper.writeValueAsString(studentService.findAll()));
        } else if (endpoint.startsWith("/students/")) {
            Long id = Long.parseLong(endpoint.split("/")[2]);
            responseBuilder
                    .setStatus("200 OK")
                    .setBody(objectMapper.writeValueAsString(studentService.findById(id)));
        } else if (endpoint.equals("/teachers")) {
            responseBuilder
                    .setStatus("200 OK")
                    .setBody(objectMapper.writeValueAsString(teacherService.findAll()));
        } else {
            responseBuilder
                    .setStatus("404 Not Found")
                    .setBody("Endpoint not supported: " + endpoint);
        }
    }

    private void handlePost(SingleRequest request, SingleResponse.Builder responseBuilder) throws JsonProcessingException, InvalidProtocolBufferException {
        String endpoint = request.getEndpoint();
        String json = JsonFormat.printer().print(request.getBody());

        if (endpoint.equals("/students")) {
            StudentDTO student = objectMapper.readValue(json, StudentDTO.class);
            responseBuilder
                    .setStatus("200 OK")
                    .setBody(objectMapper.writeValueAsString(studentService.create(student)));
        } else if (endpoint.equals("/teachers")) {
            TeacherDTO teacher = objectMapper.readValue(json, TeacherDTO.class);
            responseBuilder
                    .setStatus("200 OK")
                    .setBody(objectMapper.writeValueAsString(teacherService.save(teacher)));
        } else {
            responseBuilder
                    .setStatus("404 Not Found")
                    .setBody("Endpoint not supported: " + endpoint);
        }
    }
}
