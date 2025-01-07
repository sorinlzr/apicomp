package net.pfsnc.apicomp.fh.controller.rest;


import net.pfsnc.apicomp.fh.model.University;
import net.pfsnc.apicomp.fh.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/universities")
public class UniversityController {

    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public List<University> getAllUniversities() {
        return universityService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<University> getUniversityById(@PathVariable Long id) {
        return universityService.findById(id);
    }

    @PostMapping
    public University createUniversity(@RequestBody University university) {
        return universityService.save(university);
    }
}
