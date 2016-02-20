package server.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Repository;
import server.model.Task;
import server.util.HibernateUtil;

import java.util.List;

/**
 * Created by Narcis2007 on 20.01.2016.
 */
@Repository
public class TaskRepository {

    public static List getAll() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        List tasks = session.createQuery("from Task").list();
        session.getTransaction().commit();
        session.close();
        return tasks;
    }

    public Task getById(int id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        Task t= (Task) session.get(Task.class,id);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    public void update(Task t) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }
}
