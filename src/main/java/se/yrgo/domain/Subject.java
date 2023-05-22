package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true,nullable = false)
    private String subjectName;
    private int numberOfSemesters;
    @ManyToMany
    private Set<Tutor> tutors;

    public Subject() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(subjectName, subject.subjectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectName);
    }

    public Subject(String subjectName, int numberOfSemesters) {
        this.subjectName = subjectName;
        this.numberOfSemesters = numberOfSemesters;
        this.tutors = new HashSet<Tutor>();
    }

    public void addTutorToSubject(Tutor tutor){
        this.tutors.add(tutor);
    }

    public Set<Tutor> getTutors(){
        return tutors;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
