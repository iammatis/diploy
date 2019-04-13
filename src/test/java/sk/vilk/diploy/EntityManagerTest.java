package sk.vilk.diploy;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerTest {

    private EntityManagerFactory emf;

    @BeforeTest
    public void setup() {
        emf = new EntityManagerFactoryImpl();
    }

    @AfterTest
    public void teardown() {
        emf.close();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void closeTest() {
        EntityManager em = emf.createEntityManager();
        Assert.assertTrue(em.isOpen());

        em.close();

        TestObject testObject = new TestObject();
        em.find(TestObject.class, testObject);
    }

}
