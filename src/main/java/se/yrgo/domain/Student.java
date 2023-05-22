package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false)
    private String enrollmentID;
    private String name;
    //@ManyToOne
    //@JoinColumn(name = "TUTOR_FK")
    //private Tutor tutor;
    @Column(name = "NUM_COURSES")
    private Integer numberOfCourses;
    @Embedded
    private Address address;

    public Student(String name, String enrollmentID) {
        this.name = name;
        this.enrollmentID = enrollmentID;
        this.numberOfCourses = 10;
    }

    public Student(String name, String enrollmentID, String street, String city,
                   String zipCode){
        this.name = name;
        this.enrollmentID= enrollmentID;
        this.address = new Address(street,city,zipCode);
    }

    public Student() {

    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address newAddress) {
        this.address = newAddress;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(enrollmentID, student.enrollmentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentID);
    }

    @Override

    public String toString() {
        return name + " lives at: " + address;
    }




    /*public void allocateTutor(Tutor tutor){
        this.tutor = tutor;
    }

    public String getTutorName(){
        return tutor.getName();
    }*/

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public int getId() {
        return id;
    }
}
