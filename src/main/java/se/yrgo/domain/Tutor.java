package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity
@NamedQuery(name = "salaryAbove10", query = "SELECT t FROM Tutor AS t WHERE salary > 10000")
public class Tutor {
    @Column(unique = true, nullable = false)
    private String tutorId;
    private String name;
    private int salary;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TUTOR_FK")
    private Set<Student> teachingGroup;
    @ManyToMany(mappedBy = "tutors")
    private Set<Subject> subjectsToTeach;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Tutor() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(tutorId, tutor.tutorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tutorId);
    }

    public Tutor(String tutorId, String name, int salary) {
        this.tutorId = tutorId;
        this.name = name;
        this.salary = salary;
        this.teachingGroup = new HashSet<Student>();
        this.subjectsToTeach = new HashSet<Subject>();
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "tutorId='" + tutorId + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

    public void createStudentAndAddtoTeachingGroup(String studentName,
                                                   String enrollmentID, String street, String city,
                                                   String zipcode) {
        Student student = new Student(studentName, enrollmentID,
                street, city, zipcode);
        this.addStudentToTeachingGroup(student);
    }

    public String getTutorId() {
        return tutorId;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public void addSubjectsToTeach(Subject subject) {
        this.subjectsToTeach.add(subject);
        subject.getTutors().add(this);
    }

    public void addStudentToTeachingGroup(Student newStudent) {
        this.teachingGroup.add(newStudent);
    }

    public Set<Student> getTeachingGroup() {
        Set<Student> unmodifiable = Collections.unmodifiableSet(this.teachingGroup);
        return unmodifiable;
    }
}
