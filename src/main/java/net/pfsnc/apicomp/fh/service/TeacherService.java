package net.pfsnc.apicomp.fh.service;

import lombok.RequiredArgsConstructor;
import net.pfsnc.apicomp.fh.dto.TeacherDTO;
import net.pfsnc.apicomp.fh.mapper.TeacherMapper;
import net.pfsnc.apicomp.fh.model.Teacher;
import net.pfsnc.apicomp.fh.repository.TeacherRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<TeacherDTO> findAll() {
        return teacherRepository.findAll().stream().map(TeacherMapper::toTeacherDTO).toList();
    }

    public List<TeacherDTO> findAllWithPagination(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return teacherRepository.findAll(pageable).map(TeacherMapper::toTeacherDTO).toList();
    }

    public TeacherDTO findById(Long id) {
        return teacherRepository.findById(id).map(TeacherMapper::toTeacherDTO).orElse(null);
    }

    public TeacherDTO save(TeacherDTO teacher) {
        Teacher newTeacher = new Teacher();
        newTeacher.setName(teacher.getName());
        newTeacher.setDepartment(teacher.getDepartment());
        newTeacher = teacherRepository.save(newTeacher);
        return TeacherMapper.toTeacherDTO(newTeacher);
    }

    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
}