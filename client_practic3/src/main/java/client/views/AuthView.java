package client.views;

import client.ClientPractic3Application;
import client.service.AppService;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Narcis2007 on 18.01.2016.
 */
public class AuthView extends VBox {

    private static  String LOGIN = "Login";
    private static  String CANCEL = "Cancel";
    private ClientPractic3Application app;
    private AppService service;
    private static  Log log = LogFactory.getLog(AuthView.class);
    private  Label statusLabel;
    private  TextField username;
    private  PasswordField password;
    private  Button authButton;
    private  ProgressIndicator progressIndicator;

    public AuthView(ClientPractic3Application app, AppService controller) {

        this.app = app;
        this.service = controller;
        ObservableList<Node> children = getChildren();
        // title
        children.add(new Text("Welcome"));
        // username (label + textfield)
        statusLabel=new Label();
        statusLabel.setVisible(false);
        children.add(statusLabel);
        children.add(new Label("Username:"));
        username = new TextField();
        children.add(username);
        // password (label + textfield)
        children.add(new Label("Password:"));
        password = new PasswordField();
        children.add(password);
        // auth button (login or cancel)
        authButton = new Button(LOGIN);
        authButton.setOnAction(authActionHandler);
        children.add(authButton);
        // progress indicator
        progressIndicator = new ProgressIndicator();
        children.add(progressIndicator);
        // set pre-authenticating state

        setState(LOGIN);
        log.info("authentication view prepared");
    }

    private void setState(String authText) {
        authButton.setText(authText);
        boolean authenticating = authText.equals(CANCEL);
        progressIndicator.setVisible(authenticating);
    }


    private Service<Boolean> authService;

    private final EventHandler<ActionEvent> authActionHandler= btnEvent->{
        String authButtonText = authButton.getText();
        log.info(authButtonText + " button triggered");
        if (authButtonText.equals(LOGIN)) {
            setState(CANCEL);
            String usernameText = username.getText();
            String passwordText = password.getText();
            authService = service.authService(usernameText, passwordText); // just a reference to an async call/task
            authService.setOnSucceeded(e -> { // prepare what to do when the call succeeds
                Boolean authenticated = (Boolean) e.getSource().getValue();
                log.info("auth service succeeded,result " + authenticated); // executed on app thread
                setState(LOGIN);
                if(authenticated==false){
                    statusLabel.setText("wrong username or password!");
                    statusLabel.setVisible(true);
                }
                else
                    this.app.listView();//nu cred ca e bun, schimb cu un viewcontroller
            });
            authService.setOnFailed(e -> { // prepare what to do when the call fails
                setState(LOGIN); // executed on app thread
                Throwable exception = e.getSource().getException();
                log.warn("auth service failed", exception);
//                AlertUtils.showError(exception);
            });
            authService.setOnCancelled(e -> { // prepare what to do when the call was cancelled
                setState(CANCEL); // executed on app thread
                log.info("auth service cancelled");
            });
            log.info("starting auth service");
            authService.start(); // start the async call/task (from app thread)
            // the task is executed on background threads
        } else {
            if (authService != null) {
                authService.cancel(); // cancel the call from app thread
                setState(LOGIN);
            }
        }
    };
}
