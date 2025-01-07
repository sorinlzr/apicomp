package net.pfsnc.apicomp.fh.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import net.pfsnc.apicomp.fh.model.Teacher;
import net.pfsnc.apicomp.fh.repository.TeacherRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TeacherDeserializer extends JsonDeserializer<Teacher> {

    private final TeacherRepository teacherRepository;

    @Override
    public Teacher deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Long id = p.getLongValue();
        return teacherRepository.findById(id).orElseThrow(() -> new IOException("Teacher not found"));
    }
}