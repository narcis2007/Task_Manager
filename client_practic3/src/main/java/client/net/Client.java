package client.net;

import client.model.Task;
import client.model.User;
import client.service.AuthHttpComponentsClientHttpRequestFactory;
import org.apache.http.HttpHost;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Narcis2007 on 20.01.2016.
 */
public class Client {

    private static Client client=null;
    public static final String URL = "http://localhost:8080/";
    private final HttpHost host = new HttpHost("localhost", 8080, "http");
    private User user;

    private RestTemplate restTemplate = new RestTemplate();
    private AuthHttpComponentsClientHttpRequestFactory requestFactory;

    private Client(){
        requestFactory=new AuthHttpComponentsClientHttpRequestFactory(host,user);
        restTemplate = new RestTemplate(requestFactory);
    }


    public Boolean authenticate(String username, String password) {
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("username",username);
        vars.put("password",password);

        boolean authenticated=restTemplate.postForObject(URL+"authenticate",vars,Boolean.class);
        if(authenticated) {
            user = new User(username, password);
            requestFactory.setUser(user);
        }
        return authenticated;
    }



    public static Client getClient() {
        if (client==null)
            client=new Client();
        return client;
    }

    public Task[] getTasks() {
        return restTemplate.getForObject(URL+"getTasks",Task[].class);
    }

    public Boolean openTask(int taskId) {
        Map<String,String> vars=new HashMap<>();
        vars.put("id",String.valueOf(taskId));
        return restTemplate.postForObject(URL+"openTask",vars,Boolean.class);
    }

    public Boolean closeTask(int taskId) {
        Map<String,String> vars=new HashMap<>();
        vars.put("id",String.valueOf(taskId));
        return restTemplate.postForObject(URL+"closeTask",vars,Boolean.class);
    }
}
