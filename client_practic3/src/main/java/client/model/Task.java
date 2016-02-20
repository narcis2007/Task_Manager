package client.model;

/**
 * Created by Narcis2007 on 20.01.2016.
 */
public class Task {
    private int id;
    private String username;
    private String status;

    public Task() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String text;


    @Override
    public String toString() {
        return id +" "+ username + " "+ status + " "+ text ;
    }
}
