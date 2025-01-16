package net.pfsnc.apicomp.fh.controller.graphql;

import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.service.StudentService;
import org.reactivestreams.Publisher;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class StudentControllerGraphQL {
    private final StudentService studentService;

    private final AtomicLong studentCount;
    private FluxSink<Long> studentCountSink;
    private final Flux<Long> studentCountPublisher;

    public StudentControllerGraphQL(StudentService studentService) {
        this.studentService = studentService;
        studentCount = new AtomicLong(studentService.count());
        this.studentCountPublisher = Flux.<Long>create(emitter -> this.studentCountSink = emitter, FluxSink.OverflowStrategy.LATEST).share();
    }

    @QueryMapping
    public List<StudentDTO> students(@Argument Integer limit, @Argument Integer offset) {
        System.out.println("Fetching students with limit: " + limit + ", offset: " + offset);

        int actualLimit = (limit != null) ? limit : 10;
        int actualOffset = (offset != null) ? offset : 0;
        return studentService.findAllWithPagination(actualLimit, actualOffset);
    }

    @QueryMapping
    public StudentDTO student(@Argument Long id) {
        return studentService.findById(id);
    }

    @MutationMapping
    public StudentDTO createStudent(@Argument String name, @Argument String email) {
        StudentDTO student = new StudentDTO();
        student.setName(name);
        student.setEmail(email);

        studentCount.incrementAndGet();
        if (studentCountSink != null) {
            studentCountSink.next(studentCount.get());
        }
        return studentService.save(student);
    }

    @MutationMapping
    public StudentDTO updateStudent(@Argument Long id, @Argument String name, @Argument String email) {
        StudentDTO studentDTO = studentService.findById(id);
        if (studentDTO == null) {
            return null;
        }
        studentDTO.setName(name);
        studentDTO.setEmail(email);
        return studentService.save(studentDTO);
    }

    @SubscriptionMapping
    public Publisher<Long> studentCount() {
        return studentCountPublisher;
    }
}

