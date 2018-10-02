package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.BonusModel;
import model.PerformanceModel;
import model.UserModel;
import model.datastructures.Occupation;
import model.datastructures.Performance;
import model.datastructures.User;

public class BonusController {
    @FXML public ChoiceBox<User> actorChoiceBox;

    @FXML public ListView<Performance> pastPerformanceList;

    @FXML public TextField bonusAmountTextField;

    @FXML public Button backButton;
    @FXML public Button giveBonusButton;

    private UserModel userModel = new UserModel();
    private BonusModel bonusModel = new BonusModel();
    private PerformanceModel performanceModel = new PerformanceModel();

    @FXML
    public void initialize() {
        initBackButton();
        initGiveBonusButton();
        initPastPerformanceList();
    }

    private void initBackButton() {
        backButton.setOnMouseClicked(e -> ScreenHelper.getInstance().openScreenByOccupation(e, Occupation.DIRECTOR));
    }

    private void initPastPerformanceList() {
        pastPerformanceList.getItems().setAll(performanceModel.getAllPastPerformances());
        pastPerformanceList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) {
                updateActorsChoiceBox(newValue);
            }
        });
    }

    private void initGiveBonusButton() {
        giveBonusButton.setOnMouseClicked(event -> {
            try {
                bonusModel.giveBonusTo(actorChoiceBox.getSelectionModel().getSelectedItem(), Float.valueOf(bonusAmountTextField.getText()));
                ScreenHelper.getInstance().showDialog("Успех", "Запрос на премию создан");
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Не удалось создать запрос на бонус", e.getMessage());
            } catch (NumberFormatException e) {
                ScreenHelper.getInstance().showError("Не удалось создать запрос на бонус", "Неверная сумма");
            }
        });
    }

    private void updateActorsChoiceBox(Performance performance) {
        actorChoiceBox.getItems().setAll(userModel.getActorsOnPerformance(performance));
    }
}
