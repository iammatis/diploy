package sk.vilk.diploy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class TestObject implements Serializable {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private List<String> phoneNumbers;
    private Double salary;

    public TestObject(String firstName, String lastName, Date birthDay, List<String> phoneNumbers, Double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.phoneNumbers = phoneNumbers;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", phoneNumbers=" + phoneNumbers +
                ", salary=" + salary +
                '}';
    }
}
