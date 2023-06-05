package iss.biblioteca.Repository;

import iss.biblioteca.Domain.User;
import iss.biblioteca.Domain.UserType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserRepository {
    private SessionFactory sessionFactory;
    public UserRepository(String url) throws Exception {
        setUp();
        if(sessionFactory==null) {
            System.out.println("sess fact is null");
            tearDown();
        }
    }
    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!

        // Create the SessionFactory from hibernate.cfg.xml
        //Configuration configuration = new Configuration();
        //configuration.configure("hibernate.cfg.xml");
        System.out.println("Hibernate Configuration loaded");

        //final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            //configuration.addAnnotatedClass(User.class);
            //this.sessionFactory = configuration.buildSessionFactory(registry);
            this.sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();


            System.out.println("am facut session factory");

        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            System.out.println("throwed exception " + e);
            e.printStackTrace();
        }
    }
    protected void tearDown() throws Exception {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public void add(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            //
            session.persist(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    public User getElem(String un, String pw) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            //
            String q = "from User where username= '" + un + "' and password = '" +pw+"'";

            List result = session.createQuery(q ).list();

            if(result.size()==1){return (User)result.get(0);}
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ban(User u){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            //
            User user = session.get(User.class, u.getUsername()); // Replace userId with the actual identifier

            user.setUserType(UserType.BANNED);
            session.saveOrUpdate(user);

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
