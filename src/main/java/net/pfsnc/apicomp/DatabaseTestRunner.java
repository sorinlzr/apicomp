package net.pfsnc.apicomp;

import net.pfsnc.apicomp.fh.dto.TeacherDTO;
import net.pfsnc.apicomp.fh.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnExpression("${testRunner.database:true}")
public class DatabaseTestRunner implements CommandLineRunner {

    private final TeacherService teacherService;

    @Autowired
    public DatabaseTestRunner(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public void run(String... args) {
        List<TeacherDTO> teachers = teacherService.findAll();
        teachers.stream().limit(50).forEach(teacher -> System.out.println(teacher.getName()));
    }
}