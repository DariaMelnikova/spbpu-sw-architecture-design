package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
import model.datastructures.Occupation;

import java.io.IOException;
import java.util.Objects;

class ScreenHelper {
    private static ScreenHelper instance = new ScreenHelper();

    static ScreenHelper getInstance() {
        return instance;
    }

    private ScreenHelper() {}

    void openScreen(InputEvent event, String path, String title) {
        try {
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(path))));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.sizeToScene();
            stage.show();

            ((Node)event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void openScreenByOccupation(InputEvent event, Occupation occupation) {
        switch (occupation) {
            case ACTOR:
                ScreenHelper.getInstance().openScreen(event, "view/actors.fxml", "Для актёров");
                break;
            case VIEWER:
                ScreenHelper.getInstance().openScreen(event, "view/audience.fxml", "Для зрителей");
                break;
            case DIRECTOR:
                ScreenHelper.getInstance().openScreen(event, "view/directors.fxml", "Для режисёров");
                break;
            case ADMINISTRATOR:
                ScreenHelper.getInstance().openScreen(event, "view/administration.fxml", "Для администраторов");
                break;
            default:
                throw new IllegalStateException("Screen for selected occupation not founded");
        }
    }

    void openStartScreen(InputEvent event) {
        ScreenHelper.getInstance().openScreen(event, "view/start.fxml", "На вешалке");
    }

    void openBonusScreen(InputEvent event) {
        ScreenHelper.getInstance().openScreen(event, "view/bonus.fxml", "Выдать бонус");
    }

    void showDialog(String title, String text) {
        showTypedDialog(title, text, Alert.AlertType.INFORMATION);
    }

    void showError(String title, String text) {
        showTypedDialog(title, text, Alert.AlertType.ERROR);
    }

    private void showTypedDialog(String title, String text, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }
}
