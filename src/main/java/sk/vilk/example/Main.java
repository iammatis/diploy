package sk.vilk.example;

public class Main {

    public static void main(String[] args) {

//        EntityManagerFactoryImpl factory = new EntityManagerFactoryImpl();
//        EntityManager entityManager = factory.createEntityManager();
//
//        // PERSIST TEST
//        Student student = new Student(1, "name1");
//        entityManager.persist(student);
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
