package net.pfsnc.apicomp.fh.service;

import lombok.RequiredArgsConstructor;
import net.pfsnc.apicomp.fh.dto.CourseDTO;
import net.pfsnc.apicomp.fh.mapper.CourseMapper;
import net.pfsnc.apicomp.fh.model.Course;
import net.pfsnc.apicomp.fh.model.Teacher;
import net.pfsnc.apicomp.fh.repository.CourseRepository;
import net.pfsnc.apicomp.fh.repository.TeacherRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public List<CourseDTO> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(CourseMapper::toCourseDTO).toList();
    }

    public List<CourseDTO> findAllWithPagination(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return courseRepository.findAll(pageable)
                .map(CourseMapper::toCourseDTO).toList();
    }

    public CourseDTO findById(Long id) {
        return courseRepository.findById(id)
                .map(CourseMapper::toCourseDTO).orElse(null);
    }

    public CourseDTO save(CourseDTO courseDTO) {
        Course newCourse = new Course();
        newCourse.setTitle(courseDTO.getTitle());
        newCourse.setDescription(courseDTO.getDescription());

        Teacher teacher = teacherRepository.findById(courseDTO.getTeacherId()).orElse(null);
        newCourse.setTeacher(teacher);

        newCourse = courseRepository.save(newCourse);
        return CourseMapper.toCourseDTO(newCourse);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
}