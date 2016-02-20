package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerPractic3Application {

	public static void main(String[] args) {

		SpringApplication.run(ServerPractic3Application.class, args);

//		SessionFactory sf = HibernateUtil.getSessionFactory();
//
//		Session session = sf.openSession();
//
//		session.beginTransaction();
//		Employee p1=new Employee("alex","alex");
//		Employee p2=new Employee("ana","ana");
//		Employee p3=new Employee("teo","teo");
//		Task t1=new Task(1,"todo","implement domain");
//		Task t2=new Task(2,"todo","implement server");
//		Task t3=new Task(3,"todo","implement client");
//		session.save(t1);
//		session.save(t2);
//		session.save(t3);
//		session.getTransaction().commit();
//		session.close();
	}
}
