package net.pfsnc.apicomp.fh.service;

import lombok.RequiredArgsConstructor;
import net.pfsnc.apicomp.fh.dto.EnrollmentDTO;
import net.pfsnc.apicomp.fh.mapper.EnrollmentMapper;
import net.pfsnc.apicomp.fh.model.Course;
import net.pfsnc.apicomp.fh.model.Enrollment;
import net.pfsnc.apicomp.fh.model.Student;
import net.pfsnc.apicomp.fh.repository.CourseRepository;
import net.pfsnc.apicomp.fh.repository.EnrollmentRepository;
import net.pfsnc.apicomp.fh.repository.StudentRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public List<EnrollmentDTO> findAll(Pageable pageable) {
        return enrollmentRepository.findAll(pageable)
                .map(EnrollmentMapper::toEntrollmentDTO).toList();
    }

    public List<EnrollmentDTO> findByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(EnrollmentMapper::toEntrollmentDTO)
                .toList();
    }

    public EnrollmentDTO findById(Long id) {
        return enrollmentRepository.findById(id).map(EnrollmentMapper::toEntrollmentDTO).orElse(null);
    }

    public EnrollmentDTO save(EnrollmentDTO enrollment) {
        Enrollment newEnrollment = new Enrollment();
        Student student = studentRepository.findById(enrollment.getStudentId()).orElse(null);
        Course course = courseRepository.findById(enrollment.getCourseId()).orElse(null);
        newEnrollment.setCourse(course);
        newEnrollment.setStudent(student);
        newEnrollment.setEnrollmentDate(enrollment.getEnrollmentDate());
        newEnrollment = enrollmentRepository.save(newEnrollment);
        return EnrollmentMapper.toEntrollmentDTO(newEnrollment);
    }

    public void deleteById(Long id) {
        enrollmentRepository.deleteById(id);
    }
}