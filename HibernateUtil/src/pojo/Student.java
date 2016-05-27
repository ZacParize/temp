package pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = ("student"))
@NamedQuery(name="findStudentByLastName",query="select s from Student s where s.lastName = :lastName")
public class Student implements Serializable {
    @Id
    @GenericGenerator(name="auto_inc", strategy = "increment")
    @GeneratedValue(generator="auto_inc")
    @Column(name = ("ID"))
    private Long id;
    @Column(name = ("LAST_NAME"))
    private String lastName;
    @Column(name = ("FIRST_NAME"))
    private String firstName;
    public Student() { /* more code */ }
    public Student(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }
    public Student(Long id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }
    // getters and setters
    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + '}';
    }
}