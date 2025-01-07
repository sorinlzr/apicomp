package net.pfsnc.apicomp.fh.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pfsnc.apicomp.fh.model.utils.CourseDeserializer;
import net.pfsnc.apicomp.fh.model.utils.StudentDeserializer;

import java.util.Date;

@Entity
@Table(name = "enrollment")
@Data
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonDeserialize(using = StudentDeserializer.class)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonDeserialize(using = CourseDeserializer.class)
    private Course course;

    @Column(name = "enrollment_date")
    private Date enrollmentDate;

}
