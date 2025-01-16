package net.pfsnc.apicomp.fh.service;

import lombok.RequiredArgsConstructor;
import net.pfsnc.apicomp.fh.dto.StudentDTO;
import net.pfsnc.apicomp.fh.mapper.StudentMapper;
import net.pfsnc.apicomp.fh.model.Student;
import net.pfsnc.apicomp.fh.repository.StudentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<StudentDTO> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(StudentMapper::toStudentDTO).toList();
    }

    public List<StudentDTO> findAllWithPagination(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return studentRepository.findAll(pageable).map(StudentMapper::toStudentDTO).toList();
    }

    public StudentDTO findById(Long id) {
        return studentRepository.findById(id).map(StudentMapper::toStudentDTO).orElse(null);
    }

    public StudentDTO save(StudentDTO student) {
        Student newStudent = new Student();
        newStudent.setName(student.getName());
        newStudent.setEmail(student.getEmail());
        newStudent = studentRepository.save(newStudent);
        return StudentMapper.toStudentDTO(newStudent);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public long count() {
        return studentRepository.count();
    }
}