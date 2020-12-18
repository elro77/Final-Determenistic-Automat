package boundry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	/**
	 * opens login window and creates a new instance of login boundary
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/boundry/AutomationGeneratorWindow.fxml"));
			Scene scene = new Scene(loader.load());

			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Automation Generator");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		launch(args);
	}

}
