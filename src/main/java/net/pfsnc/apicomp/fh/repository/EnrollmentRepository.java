package net.pfsnc.apicomp.fh.repository;

import net.pfsnc.apicomp.fh.model.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Page<Enrollment> findAll(Pageable pageable);
}