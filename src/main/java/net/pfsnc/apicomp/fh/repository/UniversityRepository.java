package net.pfsnc.apicomp.fh.repository;

import net.pfsnc.apicomp.fh.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
}