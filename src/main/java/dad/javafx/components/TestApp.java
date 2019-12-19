package dad.javafx.components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		/*
		 * ListSelector<String> listSelector = new ListSelector<>();
		 * listSelector.setLeftTitle("Jugadores");
		 * listSelector.setRightTitle("Participantes");
		 * listSelector.getLeftItems().addAll( "Perico", "Palotes", "Menganita",
		 * "Fulanito" );
		 */

		DateChooser date = new DateChooser();
		BorderPane root = new BorderPane();

		// root.setCenter(listSelector);
		root.setCenter(date);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("Custom components test app");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
