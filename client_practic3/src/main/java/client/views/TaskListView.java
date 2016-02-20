package client.views;

import client.ClientPractic3Application;
import client.model.Task;
import client.service.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Narcis2007 on 20.01.2016.
 */
public class TaskListView extends HBox {
    private final ClientPractic3Application application;
    private final AppService service;

    private static Log log = LogFactory.getLog(TaskListView.class);
    private final ExecutorService executorService;
    private final ProgressIndicator progress;
    private Label notificationLabel;
    private Service<Boolean> serv;
    private boolean succeded;
    private Service<Task[]> s=null;
    private int taskId;

    public TaskListView(ClientPractic3Application application, AppService service) {
        this.executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.application = application;
        this.service = service;



        ObservableList<Node> children = getChildren();

        notificationLabel=new Label("");
        children.add(notificationLabel);

        progress=new ProgressIndicator();
        progress.setVisible(false);
        children.add(progress);
        Button openTaskButton = new Button("Open");
        openTaskButton.setOnAction((event)->{
            if(openTaskButton.getText().equals("Open")) {
                progress.setVisible(true);
                serv = service.openTask(taskId);
                openTaskButton.setText("Cancel");
                serv.setOnSucceeded((ok) -> {
                    log.info("opened task" + ok);
                    progress.setVisible(false);
                    openTaskButton.setText("Open");
                });
                serv.setOnCancelled((e) -> {
                    progress.setVisible(false);
                    openTaskButton.setText("Open");
                });
                serv.start();
            }
            else{
                serv.cancel();
            }
        });

        children.add(openTaskButton);

        Button closeTaskButton = new Button("Close");
        closeTaskButton.setOnAction((event)->{
            if(closeTaskButton.getText().equals("Close")) {
                progress.setVisible(true);
                serv = service.closeTask(taskId);
                closeTaskButton.setText("Cancel");
                serv.setOnSucceeded((ok) -> {
                    log.info("closed task" + ok);
                    progress.setVisible(false);
                    closeTaskButton.setText("Close");
                });
                serv.setOnCancelled((e) -> {
                    progress.setVisible(false);
                    closeTaskButton.setText("Close");
                });
                serv.start();
            }
            else{
                serv.cancel();
            }
        });

        children.add(closeTaskButton);


        ObservableList<Task> items = FXCollections.observableArrayList ();
        ListView<Task> list = new ListView<>();
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Task t=list.getSelectionModel().getSelectedItem();
                log.info("clicked on: "+ t);

                if(t==null) {
                    openTaskButton.setDisable(true);
                    closeTaskButton.setDisable(true);
                }
                else {
                    taskId=t.getId();
                    openTaskButton.setDisable(!(t.getStatus().equals("todo")));
                    closeTaskButton.setDisable(!(t.getStatus().equals("inprogress") && t.getUsername().equals(service.getUsername())));
                }
            }
        });
        executorService.submit(()->{
            while(true){
                succeded=false;
                s = service.getTaskService();
                s.setOnSucceeded(e->{

                    Task[] tasks=(Task[]) e.getSource().getValue();
                    items.setAll(tasks);
                    list.setItems(items);
                    succeded=true;

                    if(service.isUpdated(tasks)){
                        notificationLabel.setText("tasks have been updated");
                    }
                    else
                        notificationLabel.setText("");
                });
                s.start();
                Thread.sleep(3000);
                if(succeded!=true)
                    s.cancel();
            }

        });

        children.addAll(list);
    }

}
