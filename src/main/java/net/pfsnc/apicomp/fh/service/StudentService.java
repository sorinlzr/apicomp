package net.pfsnc.apicomp.fh.service;

import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.mapper.StudentMapper;
import net.pfsnc.apicomp.fh.model.Student;
import net.pfsnc.apicomp.fh.repository.StudentRepository;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Transactional
@Service
public class StudentService {

    private AtomicLong studentCount;
    private FluxSink<Long> studentCountSink;
    private final Flux<Long> studentCountPublisher = Flux.<Long>create(emitter -> this.studentCountSink = emitter, FluxSink.OverflowStrategy.LATEST).share();

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.studentCount = new AtomicLong(studentRepository.count());
    }


    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream().map(StudentMapper::toStudentDTO).toList();
    }

    public List<StudentDTO> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(StudentMapper::toStudentDTO).toList();
    }

    public StudentDTO findById(Long id) {
        return studentRepository.findById(id).map(StudentMapper::toStudentDTO).orElse(null);
    }

    public StudentDTO create(StudentDTO student) {
        Student newStudent = new Student();
        newStudent.setName(student.getName());
        newStudent.setEmail(student.getEmail());
        newStudent = studentRepository.save(newStudent);

        studentCount.incrementAndGet();
        if (studentCountSink != null) {
            studentCountSink.next(studentCount.get());
        }

        return StudentMapper.toStudentDTO(newStudent);
    }

    public boolean deleteById(Long id) {
        StudentDTO studentDTO = findById(id);
        if (studentDTO == null) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    public Publisher<Long> getStudentCountPublisher() {
        return studentCountPublisher;
    }
}