package se.yrgo.test;

import jakarta.persistence.*;

import se.yrgo.domain.Student;
import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

import java.util.List;
import java.util.Set;

public class HibernateTest {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

    public static void main(String[] args) {
        setUpData();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        tx.commit();
        em.close();
    }

    public static void setUpData() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Subject mathematics = new Subject("Mathematics", 2);
        Subject science = new Subject("Science", 2);
        Subject programming = new Subject("Programming", 3);
        em.persist(mathematics);
        em.persist(science);
        em.persist(programming);

        Tutor t1 = new Tutor("ABC123", "Johan Smith", 40000);
        t1.addSubjectsToTeach(mathematics);
        t1.addSubjectsToTeach(science);


        Tutor t2 = new Tutor("DEF456", "Sara Svensson", 20000);
        t2.addSubjectsToTeach(mathematics);
        t2.addSubjectsToTeach(science);

        Tutor t3 = new Tutor("GHI678", "Karin Lindberg", 0);
        t3.addSubjectsToTeach(programming);

        em.persist(t1);
        em.persist(t2);
        em.persist(t3);


        t1.createStudentAndAddtoTeachingGroup("Jimi Hendriks", "1-HEN-2019", "Street 1", "city 2", "1212");
        t1.createStudentAndAddtoTeachingGroup("Bruce Lee", "2-LEE-2019", "Street 2", "city 2", "2323");
        t3.createStudentAndAddtoTeachingGroup("Roger Waters", "3-WAT-2018", "Street 3", "city 3", "34343");


        // 1. Navigating across relationships (Using member of)
        System.out.println("1. Navigating across relationships (Using member of)");
        science = em.find(Subject.class, 2);
        TypedQuery query1 = em.createQuery("SELECT tutor.teachingGroup FROM Tutor as tutor WHERE :subject MEMBER OF tutor.subjectsToTeach", Set.class);
        query1.setParameter("subject", science);
        List<Student> students = query1.getResultList();
        for (Student student : students){
            System.out.println(student);
        }


        // 2. Report Query- Multiple fields (Use join)
        System.out.println();
        System.out.println("2. Report Query- Multiple fields (Use join)");
        Query query2 = em.createQuery("FROM Tutor AS tutor JOIN tutor.teachingGroup AS student");
        List<Object[]> results = query2.getResultList();
        for (Object[] result : results) {
            System.out.println("Student: " + result[1] + "---------------------------- Tutor: "+ result[0]);
        }

        // 3. Report Query- Aggregation
        System.out.println();
        System.out.println("3. Report Query- Aggregation");
        Query query3 = em.createQuery("SELECT AVG(s.numberOfSemesters) FROM Subject AS s");
        Double averageSemesterLength = (Double) query3.getSingleResult();
        System.out.println(averageSemesterLength);

        // 4. Query With Aggregation
        System.out.println();
        System.out.println("4. Query With Aggregation");
        Query query4 = em.createQuery("SELECT MAX(t.salary) FROM Tutor AS t");
        int maxSalary = (int) query4.getSingleResult();
        System.out.println(maxSalary);

        // 5. NamedQuery
        System.out.println();
        System.out.println("5. NamedQuery");
        Query query5 = em.createNamedQuery("salaryAbove10");
        List<Tutor> tutors = query5.getResultList();
        for(Tutor tutor:tutors){
            System.out.println(tutor);

        }


        tx.commit();
        em.close();
    }


}
