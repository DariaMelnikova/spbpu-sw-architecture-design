import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String START_SCREEN_TITLE = "Театр";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(START_SCREEN_TITLE);
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/start.fxml"))));
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
