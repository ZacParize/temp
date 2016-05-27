package dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Course;
import pojo.Student;

import java.util.Set;

public class CourseDAO {

    private Session session;

    public CourseDAO(Session session) {
        this.session = session;
    }

    public Set<Student> findRegistedOnCourse(String courseTitle) {
        Set<Student> registedOnCourse;
        Query query = session.createQuery("FROM Course WHERE title=:title");
        query.setParameter("title", courseTitle);
        Course course = (Course) query.uniqueResult();
        registedOnCourse = course.getStudents();
        return registedOnCourse;
    }
    public boolean addCourse(Course course) {
        boolean flag = false;
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.saveOrUpdate(course);
            t.commit();
            flag = true;
        } catch (HibernateException e) {
            e.printStackTrace();
            t.rollback();
        }
        return flag;
    }
}
