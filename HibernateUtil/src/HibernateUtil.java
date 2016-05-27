import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    static {
        try {
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            sessionFactory = configuration.configure().buildSessionFactory(serviceRegistry);
            //sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            //sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            // make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

}