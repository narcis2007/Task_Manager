package server.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import server.model.Employee;
import server.util.HibernateUtil;

import java.util.logging.Logger;

/**
 * Created by Narcis2007 on 20.01.2016.
 */
@Repository
public class EmployeeRepository {
    SessionFactory sf = HibernateUtil.getSessionFactory();
    private static final Logger log = Logger.getLogger( EmployeeRepository.class.getName() );
    public Employee getByUsername(String username){
        log.info("getting employee by username");
        Session session = sf.openSession();
        Employee u=(Employee)session.get(Employee.class,username);
        session.close();
        return u;
    }
}
