package server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Narcis2007 on 20.01.2016.
 */
@Entity
@Table(name="tasks")
public class Task {
    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    @Column(name="id")
    private int id;

    @Column(name="status")
    private String status;

    @Column(name="text")
    private String text;

    @Column(name="username")
    private String username;


    public Task(int id, String status, String text) {

        this.id = id;
        this.status = status;
        this.text = text;
    }
}
