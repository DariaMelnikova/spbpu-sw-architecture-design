package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.UserModel;
import model.datastructures.Occupation;

public class UserCreationController {
    @FXML public TextField loginTextField;
    @FXML public TextField lastNameTextField;
    @FXML public TextField firstNameTextField;
    @FXML public PasswordField passwordTextField;
    @FXML public ChoiceBox occupationChoiceBox;
    @FXML public Button addUserButton;
    @FXML public Button backButton;

    private UserModel userModel = new UserModel();

    @FXML
    public void initialize() {
        occupationChoiceBox.getItems().setAll(Occupation.values());

        backButton.setOnMouseClicked(event -> ScreenHelper.getInstance().openScreen(event, "view/start.fxml", ""));
        addUserButton.setOnMouseClicked(event -> {
            try {
                Occupation occupation = (Occupation) occupationChoiceBox.getValue();
                userModel.addUser(
                        loginTextField.getText(),
                        firstNameTextField.getText(),
                        lastNameTextField.getText(),
                        passwordTextField.getText(),
                        occupation
                );

                ScreenHelper.getInstance().openScreenByOccupation(event, occupation);

            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка создания пользователя", e.getMessage());
            }
        });
    }
}
