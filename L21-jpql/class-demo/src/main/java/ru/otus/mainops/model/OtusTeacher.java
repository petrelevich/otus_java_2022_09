package ru.otus.mainops.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtusTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "teacher_name")
    private String name;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;
}
