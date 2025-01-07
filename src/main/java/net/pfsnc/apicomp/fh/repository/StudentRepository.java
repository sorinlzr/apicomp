package net.pfsnc.apicomp.fh.repository;

import net.pfsnc.apicomp.fh.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAll();
    Page<Student> findAll(Pageable pageable);
}