package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.RoleModel;
import model.UserModel;
import model.datastructures.Role;

public class ActorsController {
    @FXML public Button exitButton;
    @FXML public Button acceptRoleButton;
    @FXML public Button declineRoleButton;
    @FXML public ListView<Role> roleListView;

    private UserModel userModel = new UserModel();
    private RoleModel roleModel = new RoleModel();

    @FXML
    public void initialize() {
        initExitButton();
        initRoleListView();
        initAcceptRoleButton();
        initDeclineRoleButton();
    }

    private void initExitButton() {
        exitButton.setOnMouseClicked(ScreenHelper.getInstance()::openStartScreen);
    }

    private void initRoleListView() {
        roleListView.getItems().setAll(roleModel.getAllRolesForActor(userModel.getCurrentUser()));
    }

    private void initAcceptRoleButton() {
        acceptRoleButton.setOnMouseClicked(event -> {
            try {
                roleModel.acceptRole(roleListView.getSelectionModel().getSelectedItem());
                initRoleListView();
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка принятия роли", e.getMessage());
            }
        });
    }

    private void initDeclineRoleButton() {
        declineRoleButton.setOnMouseClicked(event -> {
            try {
                roleModel.removeRole(roleListView.getSelectionModel().getSelectedItem());
                initRoleListView();
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка отказа от роли", e.getMessage());
            }
        });
    }
}
