package net.pfsnc.apicomp.fh.service;

import lombok.RequiredArgsConstructor;
import net.pfsnc.apicomp.fh.model.University;
import net.pfsnc.apicomp.fh.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    public List<University> findAll() {
        return universityRepository.findAll();
    }

    public Optional<University> findById(Long id) {
        return universityRepository.findById(id);
    }

    public University save(University university) {
        return universityRepository.save(university);
    }

    public void deleteById(Long id) {
        universityRepository.deleteById(id);
    }
}