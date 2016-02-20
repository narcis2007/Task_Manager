package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.model.Employee;
import server.model.Task;
import server.repository.EmployeeRepository;
import server.repository.TaskRepository;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Narcis2007 on 20.01.2016.
 */

@RestController
public class AppService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    private static final Logger log = Logger.getLogger( AppService.class.getName() );

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public boolean authenticate(@RequestBody Map<String,String> data) {
        log.info("authenticating");
        String username = data.get("username");
        String password = data.get("password");
        Employee e=employeeRepository.getByUsername(username);
        if(e!=null&&e.getPassword().equals(password))
            return true;
        return false;
    }

    @RequestMapping(value = "/openTask", method = RequestMethod.POST)
    public boolean openTask(@RequestBody Map<String,String> data) {
        log.info("openTask");
        int id = Integer.valueOf(data.get("id"));
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Task t=taskRepository.getById(id);
        if(t.getStatus().equals("todo")){

            t.setUsername(user.getUsername());
            t.setStatus("inprogress");
            taskRepository.update(t);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/closeTask", method = RequestMethod.POST)
    public boolean closeTask(@RequestBody Map<String,String> data) {
        log.info("closeTask");
        int id = Integer.valueOf(data.get("id"));
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Task t=taskRepository.getById(id);
        if(t.getStatus().equals("inprogress")&&t.getUsername().equals(user.getUsername())){

            t.setUsername(user.getUsername());
            t.setStatus("done");
            taskRepository.update(t);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/getTasks", method = RequestMethod.GET)
    public List getTasks() {
        log.info("getTasks");

        return taskRepository.getAll();
    }


}
