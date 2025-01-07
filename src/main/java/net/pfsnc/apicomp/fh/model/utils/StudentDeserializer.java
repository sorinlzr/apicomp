package net.pfsnc.apicomp.fh.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import net.pfsnc.apicomp.fh.model.Student;
import net.pfsnc.apicomp.fh.repository.StudentRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class StudentDeserializer extends JsonDeserializer<Student> {

    private final StudentRepository studentRepository;

    @Override
    public Student deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Long id = p.getLongValue();
        return studentRepository.findById(id).orElseThrow(() -> new IOException("Student not found"));
    }
}