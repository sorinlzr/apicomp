package net.pfsnc.apicomp.fh.service;

import lombok.RequiredArgsConstructor;
import net.pfsnc.apicomp.fh.model.Enrollment;
import net.pfsnc.apicomp.fh.repository.EnrollmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public Page<Enrollment> findAll(Pageable pageable) {
        return enrollmentRepository.findAll(pageable);
    }

    public Optional<Enrollment> findById(Long id) {
        return enrollmentRepository.findById(id);
    }

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteById(Long id) {
        enrollmentRepository.deleteById(id);
    }
}