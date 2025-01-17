package net.pfsnc.apicomp.fh.grpc;

import grpcstarter.server.GrpcService;
import io.grpc.stub.StreamObserver;
import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.service.StudentService;
import reactor.core.publisher.Flux;

@GrpcService
public class StudentGrpcService extends StudentServiceGrpc.StudentServiceImplBase {

    private final StudentService studentService;

    public StudentGrpcService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void getStudent(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
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
    }


    @Override
    public void createStudent(CreateStudentRequest request, StreamObserver<StudentResponse> responseObserver) {
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
    }

    @Override
    public void getAllStudents(Empty request, StreamObserver<GetAllStudentsResponse> responseObserver) {
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
        Flux<Long> studentCountFlux = Flux.from(studentService.getStudentCountPublisher());
        studentCountFlux.map(count -> StudentCountResponse.newBuilder().setCount(count.intValue()).build())
                .doOnNext(responseObserver::onNext)
                .doOnComplete(responseObserver::onCompleted)
                .doOnError(responseObserver::onError)
                .subscribe();
    }
}
