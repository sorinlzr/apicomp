package net.pfsnc.apicomp.fh.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pfsnc.apicomp.fh.model.utils.TeacherDeserializer;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonDeserialize(using = TeacherDeserializer.class)
    private Teacher teacher;
}
