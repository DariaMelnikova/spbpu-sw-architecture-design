package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.UserModel;

public class StartController {
    @FXML public Button addUserButton;
    @FXML public Button enterButton;
    @FXML public TextField loginTextField;
    @FXML public PasswordField passwordTextField;

    UserModel userModel = new UserModel();

    @FXML
    public void initialize() {
        enterButton.setOnMouseClicked(event -> {
            try {
                ScreenHelper.getInstance().openScreenByOccupation(
                        event,
                        userModel.getUser(loginTextField.getText(), passwordTextField.getText()).getOccupation()
                );
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка входа", e.getMessage());
            }
        });
        addUserButton.setOnMouseClicked(event ->
                ScreenHelper.getInstance().openScreen(event, "view/user_creation.fxml", "Создание пользователя"));
    }
}
