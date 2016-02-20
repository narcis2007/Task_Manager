package client;

import client.net.Client;
import client.service.AppService;
import client.views.AuthView;
import client.views.TaskListView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientPractic3Application extends Application {

	private Stage stage;
	private Scene scene;
	private static Log log = LogFactory.getLog(ClientPractic3Application.class);
	private AppService service;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		log.info("authentication view");
		stage=primaryStage;
		service=new AppService(Client.getClient());
		AuthView authenticationView=new AuthView(this,service);
		scene=new Scene(authenticationView);
		stage.setScene(scene);
		stage.show();
	}

	public void listView() {
		log.info("listView");
		TaskListView listiew=new TaskListView(this,service);
		scene=new Scene(listiew);
		stage.setScene(scene);
	}
}
