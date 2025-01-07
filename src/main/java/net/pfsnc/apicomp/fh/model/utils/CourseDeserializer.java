package net.pfsnc.apicomp.fh.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import net.pfsnc.apicomp.fh.model.Course;
import net.pfsnc.apicomp.fh.repository.CourseRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CourseDeserializer extends JsonDeserializer<Course> {

    private final CourseRepository courseRepository;

    @Override
    public Course deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Long id = p.getLongValue();
        return courseRepository.findById(id).orElseThrow(() -> new IOException("Course not found"));
    }
}