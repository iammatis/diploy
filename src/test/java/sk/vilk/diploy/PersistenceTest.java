package sk.vilk.diploy;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class PersistenceTest {
    private EntityManagerFactoryImpl factory;
    private EntityManager em;
    private File mainFile;
    private File metaFile;

    @BeforeTest
    public void setupTest() {
        factory = new EntityManagerFactoryImpl();
        em = factory.createEntityManager();
        mainFile = new File("diploy.bin");
        metaFile = new File("diploy_meta.bin");
        mainFile.delete();
        metaFile.delete();
    }

    @AfterTest
    public void destroyFiles() {
        mainFile.delete();
        metaFile.delete();
        em.close();
    }

    @Test
    public void persistTest() {
        ArrayList<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("+421 123 456");
        phoneNumbers.add("+421 789 100");
        Date birthDay = new Date();
        TestObject testObject = new TestObject("First", "Last", birthDay, phoneNumbers, 123_456.789);

        em.getTransaction().begin();
        em.persist(testObject);
        em.getTransaction().commit();

        TestObject foundObject = em.find(TestObject.class, testObject.getId());

        Assert.assertEquals(foundObject.getFirstName(), "First");
        Assert.assertEquals(foundObject.getLastName(), "Last");
        Assert.assertEquals(foundObject.getBirthDay(), birthDay);
        Assert.assertEquals(foundObject.getSalary(), 123_456.789);
        Assert.assertTrue(foundObject.getPhoneNumbers().contains("+421 123 456"));
        Assert.assertTrue(foundObject.getPhoneNumbers().contains("+421 789 100"));
    }

    @Test
    public void removeTest() {
        ArrayList<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("+421 123 456");
        phoneNumbers.add("+421 789 100");
        Date birthDay = new Date();
        TestObject testObject = new TestObject("First", "Last", birthDay, phoneNumbers, 123_456.789);

        em.getTransaction().begin();
        em.persist(testObject);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.remove(testObject);
        em.getTransaction().commit();

        TestObject foundObject = em.find(TestObject.class, testObject.getId());
        Assert.assertNull(foundObject);
    }

    @Test
    public void flushTest() {
        ArrayList<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("+421 123 456");
        phoneNumbers.add("+421 789 100");
        Date birthDay = new Date();
        TestObject testObject = new TestObject("First", "Last", birthDay, phoneNumbers, 123_456.789);

        em.getTransaction().begin();
        em.persist(testObject);
        em.getTransaction().commit();

        testObject.setFirstName("New First");
        em.flush();

        TestObject foundObject = em.find(TestObject.class, testObject.getId());
        Assert.assertEquals(foundObject.getFirstName(), "New First");
    }

    @Test
    public void containsTest() {
        ArrayList<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("+421 123 456");
        phoneNumbers.add("+421 789 100");
        Date birthDay = new Date();
        TestObject testObject = new TestObject("First", "Last", birthDay, phoneNumbers, 123_456.789);

        em.getTransaction().begin();
        em.persist(testObject);
        em.getTransaction().commit();

        Assert.assertTrue(em.contains(testObject));
    }

}
