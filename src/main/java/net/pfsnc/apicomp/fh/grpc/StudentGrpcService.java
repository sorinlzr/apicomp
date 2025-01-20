package net.pfsnc.apicomp.fh.grpc;

import grpcstarter.server.GrpcService;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.exception.ValidationException;
import net.pfsnc.apicomp.fh.service.StudentService;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.util.logging.Logger;

@GrpcService
public class StudentGrpcService extends StudentServiceGrpc.StudentServiceImplBase {

    private final Logger LOGGER = Logger.getLogger(StudentGrpcService.class.getName());

    private final StudentService studentService;

    public StudentGrpcService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void getStudent(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        LOGGER.info("Getting student by id: " + request.getId());
        try {
            StudentDTO student = studentService.findById(request.getId());
            StudentResponse.Builder responseBuilder = StudentResponse.newBuilder()
                    .setId(student.getId())
                    .setName(student.getName())
                    .setEmail(student.getEmail());

            student.getEnrollments().forEach(enrollment -> {
                Enrollment enrollmentProto = Enrollment.newBuilder()
                        .setId(enrollment.getId())
                        .setCourseDescription(enrollment.getCourseTitle())
                        .setStudentId(enrollment.getStudentId().toString())
                        .setStudentName(enrollment.getStudentName())
                        .setStudentEmail(enrollment.getStudentEmail())
                        .setEnrollmentDate(enrollment.getEnrollmentDate().toString())
                        .build();
                responseBuilder.addEnrollments(enrollmentProto);
            });

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (ResponseStatusException e) {
            responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(e.getMessage())));
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal server error")));
        }
    }


    @Override
    public void createStudent(CreateStudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        try {
            validateCreateStudentRequest(request);
            LOGGER.info("Creating student with name: " + request.getName() + " and email: " + request.getEmail());
            StudentDTO student = new StudentDTO();
            student.setName(request.getName());
            student.setEmail(request.getEmail());
            student = studentService.create(student);
            StudentResponse response = StudentResponse.newBuilder()
                    .setId(student.getId())
                    .setName(student.getName())
                    .setEmail(student.getEmail())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ValidationException e) {
            responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(e.getMessage())));
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal server error")));
        }

    }

    @Override
    public void deleteStudent(StudentRequest request, StreamObserver<DeleteStudentResponse> responseObserver) {
        LOGGER.info("Deleting student by id: " + request.getId());
        try {
            boolean success = studentService.deleteById(request.getId());
            DeleteStudentResponse response = DeleteStudentResponse.newBuilder()
                    .setSuccess(success)
                    .setMessage(success ? "Student deleted successfully" : "Failed to delete student")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ResponseStatusException e) {
            responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(e.getMessage())));
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal server error")));
        }
    }

    @Override
    public void updateStudent(UpdateStudentRequest request, StreamObserver<UpdateStudentResponse> responseObserver) {
        LOGGER.info("Updating student with id: " + request.getId());
        try {
            validateUpdateStudentRequest(request);
            StudentDTO student = new StudentDTO();
            student.setId(request.getId());
            student.setName(request.getName());
            student.setEmail(request.getEmail());
            student = studentService.update(student);
            UpdateStudentResponse response = UpdateStudentResponse.newBuilder()
                    .setId(student.getId())
                    .setName(student.getName())
                    .setEmail(student.getEmail())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ValidationException e) {
            responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(e.getMessage())));
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal server error")));
        }
    }

    @Override
    public void getAllStudents(Empty request, StreamObserver<GetAllStudentsResponse> responseObserver) {
        LOGGER.info("Getting all students");
        Flux<StudentDTO> studentFlux = Flux.fromIterable(studentService.findAll());
        studentFlux.collectList()
                .map(students -> {
                    GetAllStudentsResponse.Builder responseBuilder = GetAllStudentsResponse.newBuilder();
                    students.forEach(student -> {
                        StudentResponse.Builder studentResponseBuilder = StudentResponse.newBuilder()
                                .setId(student.getId())
                                .setName(student.getName())
                                .setEmail(student.getEmail());

                        student.getEnrollments().forEach(enrollment -> {
                            Enrollment enrollmentProto = Enrollment.newBuilder()
                                    .setId(enrollment.getId())
                                    .setCourseDescription(enrollment.getCourseTitle())
                                    .setStudentId(enrollment.getStudentId().toString())
                                    .setStudentName(enrollment.getStudentName())
                                    .setStudentEmail(enrollment.getStudentEmail())
                                    .setEnrollmentDate(enrollment.getEnrollmentDate().toString())
                                    .build();
                            studentResponseBuilder.addEnrollments(enrollmentProto);
                        });

                        responseBuilder.addStudents(studentResponseBuilder.build());
                    });
                    return responseBuilder.build();
                })
                .doOnNext(responseObserver::onNext)
                .doOnError(responseObserver::onError)
                .doOnTerminate(responseObserver::onCompleted)
                .subscribe();
    }

    @Override
    public void subscribeStudentCount(Empty request, StreamObserver<StudentCountResponse> responseObserver) {
        LOGGER.info("Subscribing to student count");
        Flux<Long> studentCountFlux = Flux.from(studentService.getStudentCountPublisher());
        studentCountFlux.map(count -> StudentCountResponse.newBuilder().setCount(count.intValue()).build())
                .doOnNext(responseObserver::onNext)
                .doOnComplete(responseObserver::onCompleted)
                .doOnError(responseObserver::onError)
                .subscribe();
    }

    private void validateCreateStudentRequest(CreateStudentRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new ValidationException("Name is mandatory");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new ValidationException("Email is mandatory");
        }
        if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Email should be valid");
        }
    }

    private void validateUpdateStudentRequest(UpdateStudentRequest request) {
        if (request.getId() <= 0) {
            throw new ValidationException("ID is mandatory and must be greater than 0");
        }
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new ValidationException("Name is mandatory");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new ValidationException("Email is mandatory");
        }
        if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Email should be valid");
        }
    }
}
