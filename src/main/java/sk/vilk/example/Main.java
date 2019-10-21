package sk.vilk.example;

import org.apache.commons.lang3.SerializationUtils;
import sk.vilk.diploy.EntityManagerFactoryImpl;
import sk.vilk.diploy.file.FileManager;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactoryImpl factory = new EntityManagerFactoryImpl();
        EntityManager entityManager = factory.createEntityManager();

        Teacher foundTeacher = entityManager.find(Teacher.class, "5c07c418-410e-41ef-bc4f-5cb4a2ed1961");
        System.out.println(foundTeacher);
        List<Subject> subjects = foundTeacher.getSubjects();
        Subject subject = subjects.get(0);
        System.out.println(subject.getId());
        System.out.println(subject.getName());
        System.out.println(subject.getTeachers());
//        Class clazz = foundTeacher.getClazz();
//        System.out.println(clazz.getStudents());
//        List<Student> students = clazz.getStudents();
//        Student student = students.get(0);
//        System.out.println(student);
//        System.out.println(student.getClazz());
//        Subject subject = foundTeacher.getSubjects().get(0);
//        System.out.println(subject.getTeachers());
//        foundTeacher.setFirstName("new Jane");
//        System.out.println(entityManager.getLockMode(foundTeacher));
//        System.out.println(foundTeacher);
        entityManager.close();
        factory.close();
//
//        Subject math = new Subject("Math");
//        Subject english= new Subject("English");
//        Subject slovak = new Subject("Slovak");
//
//        Teacher teacher = new Teacher("Jane", "Doe");
//        teacher.setSubjects(List.of(math, english, slovak));
//
//        Teacher teacher2 = new Teacher("John", "Doe");
//        teacher.setSubjects(List.of(math, english, slovak));
//
//        Class clazz = new Class("Best class", teacher);
//
//        Student han = new Student("Han Solo");
//        Student mary = new Student("Mary Jane");
//        clazz.setStudents(List.of(han, mary));
//
//        teacher.setClazz(clazz);
//
//        entityManager.getTransaction().begin();
//        entityManager.persist(math);
//        entityManager.persist(english);
//        entityManager.persist(slovak);
//        entityManager.persist(han);
//        entityManager.persist(mary);
//        entityManager.persist(clazz);
//        entityManager.persist(teacher);
//        entityManager.persist(teacher2);
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        factory.close();
//        System.out.println(teacher);

        // Detach Test

//        Student student = new Student("student 1");
//        entityManager.getTransaction().begin();
//        entityManager.persist(student);
//        entityManager.getTransaction().commit();
//        System.out.println(student);
//
//        entityManager.detach(student);
//        System.out.println(entityManager.contains(student));


        // Rollback Test

//        Student student = new Student("student 1");
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.persist(student);
//            entityManager.getTransaction().commit();
//        } catch (RollbackException e) {
//            entityManager.getTransaction().rollback();
//        }
//        System.out.println(student);


        // Refresh Test

//        Student student = new Student("student 1");
//        entityManager.getTransaction().begin();
//        entityManager.persist(student);
//        entityManager.getTransaction().commit();
//        System.out.println(student);
//
//        student.setName("changed name");
//        System.out.println(student);
//        entityManager.refresh(student);
//        System.out.println(student);


        // Flush Test

//        Student student = new Student("student 1");
//        entityManager.getTransaction().begin();
//        entityManager.persist(student);
//        entityManager.getTransaction().commit();
//        System.out.println(student);

//        Student found = entityManager.find(Student.class, "456d8dc5-0f0b-48cf-82b0-abc585fb1f45");
//        System.out.println(found);
//        found.setName("reset name 2");
//        System.out.println(found);

//        entityManager.flush();
//        Student student = new Student("new student 3");
//        entityManager.getTransaction().begin();
//        entityManager.persist(student);
//        entityManager.getTransaction().commit();


        // COMMIT SPEED TEST

//        int NUM = 1_000_000;
//        Long start = System.currentTimeMillis();
//        entityManager.getTransaction().begin();
//        for (int i = 0; i < NUM; i++) {
//            Student student = new Student("student " + i);
//            entityManager.persist(student);
//        }
//        entityManager.getTransaction().commit();
//        Long end = System.currentTimeMillis();
//
//        System.out.println("time millis: " + (end- start));
//        System.out.println("time secs: " +  (TimeUnit.MILLISECONDS.toSeconds(end - start)));

//        Student student1 = entityManager.find(Student.class, "5069c302-e7a7-462e-a342-6ad5b6e99db5");
//        Student student2 = entityManager.find(Student.class, "fefff8f0-5dd3-4864-9f85-b78dd467c2e7");
//        System.out.println(student1);
//        System.out.println(student2);

//        // PERSIST TEST
//        Student student = new Student("name1");
//        entityManager.persist(student);
//
//        Student student2 = entityManager.find(Student.class, student.getId());
//        System.out.println(student2);

//
//        Student foundStudent = entityManager.find(student.getClass(), student);
//        System.out.println(foundStudent);
//
//        // REMOVE TEST
//        entityManager.remove(foundStudent);
//
//        Student notFoundStudent = entityManager.find(foundStudent.getClass(), foundStudent);
//        System.out.println(notFoundStudent);

        // MERGE TEST
//        student.setName("new name");
//        entityManager.merge(student);
//
//        Student foundStudent = entityManager.find(student.getClass(), student);
//        System.out.println(foundStudent);


        // BULK TEST
//        Student student;
//        long start = System.currentTimeMillis();
//        long last = 0;
//        for (int i = 0; i < 10000; i++) {
//            if (i % 100 == 0) {
//                long newStart = System.currentTimeMillis();
//                long current = newStart - start;
//                System.out.println(i + " " + current + " " + (current - last));
//                last = current;
//                start = newStart;
//            }
//            student = new Student(i, "student" + i);
//            entityManager.persist(student);
//        }

        // VACUUM TEST
//        List<Student> studentList = new ArrayList<>();
//        System.out.println("Vacuum test create students | START");
//
//        Student student;
//        for (int i = 1; i < 1000; i++) {
//            student = new Student(i, "student" + i);
//            studentList.add(student);
//        }
//
//        System.out.println("Vacuum test create students | END");
//
//
//
//        System.out.println("Vacuum test persist | START");
//
//        for (Student studentFromList: studentList) {
//            entityManager.persist(studentFromList);
//        }
//
////        for (Student studentFromList: studentList) {
////            entityManager.refresh(studentFromList);
////        }
////        entityManager.clear();
//
//        System.out.println("Vacuum test persist | END");
//
//
//        try {
//            System.out.println("PAUSE FOR 10 seconds START");
//            Thread.sleep(10000);
//            System.out.println("PAUSE FOR 10 seconds END");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        System.out.println("Vacuum test remove (half) | START");
//
//        for (int i = 0; i < 500; i++) {
//            Student toRemove = studentList.get(i);
//            entityManager.remove(toRemove);
//        }
//
//        System.out.println("Vacuum test remove (half) | END");
//
//
//        try {
//            System.out.println("PAUSE FOR 10 seconds START");
//            Thread.sleep(10000);
//            System.out.println("PAUSE FOR 10 seconds END");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        System.out.println("Vacuum test perform vacuum | START");
//
//        factory.getPersistenceManager().performVacuum(studentList.get(0));
//
//        System.out.println("Vacuum test perform vacuum | END");
    }

}
