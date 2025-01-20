package net.pfsnc.apicomp.fh.service;

import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.mapper.StudentMapper;
import net.pfsnc.apicomp.fh.model.Student;
import net.pfsnc.apicomp.fh.repository.StudentRepository;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
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
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return StudentMapper.toStudentDTO(existingStudent);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        studentRepository.deleteById(id);
        return true;
    }

    public StudentDTO update(StudentDTO student) {
        Student existingStudent = studentRepository.findById(student.getId()).orElse(null);
        if (existingStudent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent = studentRepository.save(existingStudent);
        return StudentMapper.toStudentDTO(existingStudent);
    }

    public Publisher<Long> getStudentCountPublisher() {
        return studentCountPublisher;
    }
}