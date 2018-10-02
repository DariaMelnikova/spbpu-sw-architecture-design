package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.PerformanceModel;
import model.RoleModel;
import model.UserModel;
import model.datastructures.Performance;
import model.datastructures.Role;
import model.datastructures.User;

public class DirectorsController {
    @FXML public Button createRole;
    @FXML public Button exitButton;
    @FXML public Button giveBonusButton;

    @FXML public ListView<Role> rolesOnPerformanceList;
    @FXML public ListView<Performance> performanceList;

    @FXML public TextField roleDescription;

    @FXML public ChoiceBox<User> actorForRoleChoiceBox;

    private UserModel userModel = new UserModel();
    private RoleModel roleModel = new RoleModel();
    private PerformanceModel performanceModel = new PerformanceModel();

    @FXML
    public void initialize() {
        initExitButton();
        initPerformanceList();
        initGiveBonusButton();
        initRoleCreationButton();
    }

    private void initExitButton() {
        exitButton.setOnMouseClicked(ScreenHelper.getInstance()::openStartScreen);
    }

    private void initPerformanceList() {
        performanceList.getItems().setAll(performanceModel.getAllUpcomingPerformancesForDirector(userModel.getCurrentUser()));
        performanceList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) {
                updateByPerformance(newValue);
            }
        });
    }

    private void initRoleCreationButton() {
        createRole.setOnMouseClicked(event -> {
            try {
                roleModel.addRole(
                        roleDescription.getText(),
                        performanceList.getSelectionModel().getSelectedItem(),
                        actorForRoleChoiceBox.getValue()
                );
                updateByPerformance(performanceList.getSelectionModel().getSelectedItem());
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка создания роли", e.getMessage());
            }
        });
    }

    private void initGiveBonusButton() {
        giveBonusButton.setOnMouseClicked(event -> ScreenHelper.getInstance().openBonusScreen(event));
    }

    private void updateByPerformance(Performance performance) {
        updateActorsChoiceBox(performance);
        updateRoleOnPerformanceList(performance);
    }

    private void updateActorsChoiceBox(Performance performance) {
        actorForRoleChoiceBox.getItems().setAll(userModel.getAvailableActorsForPerformance(performance));
    }

    private void updateRoleOnPerformanceList(Performance performance) {
        rolesOnPerformanceList.getItems().setAll(roleModel.getAllRolesOnPerformance(performance));
    }
}
