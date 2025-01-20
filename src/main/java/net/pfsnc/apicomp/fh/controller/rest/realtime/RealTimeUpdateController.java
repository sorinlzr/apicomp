package net.pfsnc.apicomp.fh.controller.rest.realtime;


import net.pfsnc.apicomp.fh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/updates")
public class RealTimeUpdateController {
    private final Logger LOGGER = Logger.getLogger(RealTimeUpdateController.class.getName());

    private final StudentService studentService;

    @Autowired
    public RealTimeUpdateController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student-count")
    public long getCurrentStudentCount() {
        LOGGER.info("Getting current student count");
        return studentService.findAll().size();
    }
}
