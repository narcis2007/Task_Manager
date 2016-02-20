package client.service;

import client.net.Client;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Narcis2007 on 20.01.2016.
 */
public class AppService {
    private final Client client;
    private String username;

    private client.model.Task[] taskCache=null;

    private static Log log = LogFactory.getLog(AppService.class);

    public AppService(Client client) {
        this.client=client;
    }

    public Service<Boolean> authService(String usernameText, String passwordText) {
        return new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        boolean ok=client.authenticate(usernameText,passwordText);
                        if(ok)
                            username=usernameText;
                        return ok;
                    }
                };
            }
        };
    }

    public Service<client.model.Task[]> getTaskService() {
        return new Service<client.model.Task[]>() {
            @Override
            protected Task<client.model.Task[]> createTask() {
                return new Task<client.model.Task[]>() {
                    @Override
                    protected client.model.Task[] call() throws Exception {

                        client.model.Task[] tasks= client.getTasks();

                        return tasks;
                    }
                };
            }
        };
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Service<Boolean> closeTask(int taskId) {
        return new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        synchronized (this) {

                            wait(2000);
                        }
                        return client.closeTask(taskId);
                    }
                };
            }
        };
    }

    public Service<Boolean> openTask(int taskId) {
        return new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        synchronized (this) {

                            wait(2000);
                        }
                        return client.openTask(taskId);
                    }
                };
            }
        };
    }

    public boolean isUpdated(client.model.Task[] tasks) {
        boolean updated=false;
        if(taskCache!=null){
            for(client.model.Task t:tasks) {
                for (client.model.Task t2 : taskCache) {
                    if (t.getId() == t2.getId() && !t.getStatus().equals(t2.getStatus()))
                        updated = true;
                }
            }

        }
        else{
            taskCache=tasks;
        }
        if(updated)
            taskCache=tasks;
        return updated;
    }
}
