package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import model.datastructures.BonusRequest;
import model.datastructures.BoughtTicket;
import model.datastructures.Occupation;
import model.datastructures.User;

import java.time.LocalDate;
import java.time.ZoneId;

public class AdministrationController {
    @FXML public Button exitButton;
    @FXML public Button giveBonusButton;
    @FXML public Button refuseBonusButton;
    @FXML public Button refuseTicketButton;
    @FXML public Button approveTicketButton;
    @FXML public Button createPerformanceButton;

    @FXML public TextArea descriptionTextArea;
    @FXML public TextField performanceNameTextField;

    @FXML public ListView<BoughtTicket> ticketList;
    @FXML public ListView<BonusRequest> bonusContestantList;

    @FXML public ChoiceBox<User> directorChoiceBox;

    @FXML public DatePicker performanceDatePicker;

    @FXML public TextField ticketPriceTextField;

    private UserModel userModel = new UserModel();
    private BonusModel bonusModel = new BonusModel();
    private TicketModel ticketModel = new TicketModel();
    private PerformanceModel performanceModel = new PerformanceModel();

    @FXML
    public void initialize() {
        initExitButton();
        initPriceField();
        initTicketList();
        initGiveBonusButton();
        initBonusRequestList();
        initRefuseTicketButton();
        initRefuseBonusButton();
        initPerformanceCreation();
        initApproveTicketButton();
    }

    private void initPerformanceCreation() {
        initDatePicker();
        directorChoiceBox.getItems().setAll(userModel.getAllUsersWithOccupation(Occupation.DIRECTOR));
        createPerformanceButton.setOnMouseClicked(event -> {
            try {
                final LocalDate performanceDate = performanceDatePicker.getValue();
                performanceModel.addPerformance(
                        performanceNameTextField.getText(),
                        descriptionTextArea.getText(),
                        directorChoiceBox.getValue(),
                        (performanceDate != null) ? performanceDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() : 0L,
                        Float.valueOf(ticketPriceTextField.getText())
                );
                ScreenHelper.getInstance().showDialog("Успех!", "Спектакль успешно создан");
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка создания спектакля", e.getMessage());
            }
        });
    }

    private void initDatePicker() {
        performanceDatePicker.setEditable(false);
        performanceDatePicker.setDayCellFactory(param -> TimeUtils.getInstance().getDayCellFactory());
        performanceDatePicker.setConverter(TimeUtils.getInstance().getDateStringConverter());
    }

    private void initPriceField() {
        ticketPriceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) ticketPriceTextField.setText(oldValue);
        });
    }

    private void initBonusRequestList() {
        updateBonusRequests();
    }

    private void initTicketList() {
        updatePendingTickets();
    }

    private void initApproveTicketButton() {
        approveTicketButton.setOnMouseClicked(event -> {
            try {
                ticketModel.approveBuying(ticketList.getSelectionModel().getSelectedItem());
                updatePendingTickets();
                ScreenHelper.getInstance().showDialog("Успех", "Одобрение выполнено");
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка одобрения", e.getMessage());
            }
        });
    }

    private void initRefuseTicketButton() {
        refuseTicketButton.setOnMouseClicked(event -> {
            try {
                ticketModel.refuseReturn(ticketList.getSelectionModel().getSelectedItem());
                updatePendingTickets();
                ScreenHelper.getInstance().showDialog("Успех", "Отказ выполнен");
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка отказа", e.getMessage());
            }
        });
    }

    private void initGiveBonusButton() {
        giveBonusButton.setOnMouseClicked(event -> {
            try {
                bonusModel.removeBonus(bonusContestantList.getSelectionModel().getSelectedItem());
                updateBonusRequests();
                ScreenHelper.getInstance().showDialog("Успешно", "Бонус выдан!");
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка выдачи бонуса", e.getMessage());
            }
        });
    }

    private void initRefuseBonusButton() {
        refuseBonusButton.setOnMouseClicked(event -> {
            try {
                bonusModel.removeBonus(bonusContestantList.getSelectionModel().getSelectedItem());
                updateBonusRequests();
                ScreenHelper.getInstance().showDialog("Успешно", "Бонус отменён");
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка выдачи бонуса", e.getMessage());
            }
        });
    }

    private void initExitButton() {
        exitButton.setOnMouseClicked(ScreenHelper.getInstance()::openStartScreen);
    }

    private void updatePendingTickets() {
        ticketList.getItems().setAll(ticketModel.getPendingBoughtTickets());
    }

    private void updateBonusRequests() {
        bonusContestantList.getItems().setAll(bonusModel.getAll());
    }
}
