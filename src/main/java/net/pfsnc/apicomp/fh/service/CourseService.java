package net.pfsnc.apicomp.fh.service;

import lombok.RequiredArgsConstructor;
import net.pfsnc.apicomp.fh.model.Course;
import net.pfsnc.apicomp.fh.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public List<Course> findAllWithPagination(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return courseRepository.findAll(pageable).getContent();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
}