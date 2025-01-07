package net.pfsnc.apicomp.fh.controller.rest.realtime;


import net.pfsnc.apicomp.fh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
public class RealTimeUpdateController {

    private final StudentService studentService;

    @Autowired
    public RealTimeUpdateController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student-count")
    public long getCurrentStudentCount(Pageable pageable) {
        return studentService.findAll(pageable).getTotalElements();
    }
}
