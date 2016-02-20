package server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Narcis2007 on 20.01.2016.
 */
@Entity
@Table(name="employees")
public class Employee {

    public Employee() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Id
        @Column(name="username")
        private String username;

    @Column(name="password")
    private String password;

    public Employee(String username, String password) {

        this.username = username;
        this.password = password;
    }
}
