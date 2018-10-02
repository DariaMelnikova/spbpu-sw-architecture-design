package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.PerformanceModel;
import model.TicketModel;
import model.TimeUtils;
import model.datastructures.BoughtTicket;
import model.datastructures.Performance;
import model.datastructures.Ticket;

import java.time.ZoneId;

public class AudienceController {
    @FXML public ListView<Performance> performanceList;
    @FXML public ListView<BoughtTicket> performanceWithBoughtTicketList;

    @FXML public Button exitButton;
    @FXML public Button buyTicketButton;
    @FXML public Button askForReturnButton;

    @FXML public Label ticketPriceLabel;

    @FXML public ChoiceBox<Ticket> sitChoiceBox;

    @FXML public DatePicker performanceDatePicker;

    @FXML public TextArea performanceDescriptionTextArea;

    private TicketModel ticketModel = new TicketModel();
    private PerformanceModel performanceModel = new PerformanceModel();

    @FXML
    public void initialize() {
        initExitButton();
        initDatePicker();
        initReturnButton();
        initSitChoiceBox();
        initBuyTicketButton();
        initBoughtTicketsList();
        initDescriptionTextArea();
        initUpcomingPerformanceList();
    }

    private void initExitButton() {
        exitButton.setOnMouseClicked(ScreenHelper.getInstance()::openStartScreen);
    }

    private void initDatePicker() {
        performanceDatePicker.setEditable(false);
        performanceDatePicker.setConverter(TimeUtils.getInstance().getDateStringConverter());
        performanceDatePicker.setDayCellFactory(param -> TimeUtils.getInstance().getDayCellFactory());
        performanceDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updatePerformanceList(newValue.atStartOfDay(ZoneId.systemDefault()).toEpochSecond());
            }
        });
    }

    private void initSitChoiceBox() {
        sitChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) ticketPriceLabel.setText(Float.toString(newValue.getPrice()));
        });
    }

    private void initReturnButton() {
        askForReturnButton.setOnMouseClicked(event -> {
            try {
                ticketModel.requestReturn(performanceWithBoughtTicketList.getSelectionModel().getSelectedItem());
                updateBoughtTicketsList();
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка возврата", e.getMessage());
            }
        });
    }

    private void initDescriptionTextArea() {
        performanceDescriptionTextArea.setEditable(false);
    }

    private void initUpcomingPerformanceList() {
        updatePerformanceList();
        performanceList.setOnMouseClicked(event -> {
            Performance performance = performanceList.getSelectionModel().getSelectedItem();
            if (performance != null) {
                performanceDescriptionTextArea.setText(performance.getDescription());
                setSitChoiceBoxValues(performance);
            }
        });
    }

    private void initBuyTicketButton() {
        buyTicketButton.setOnMouseClicked(event -> {
            try {
                ticketModel.buyTicket(sitChoiceBox.getSelectionModel().getSelectedItem());
                sitChoiceBox.getItems().remove(sitChoiceBox.getSelectionModel().getSelectedItem());
                ScreenHelper.getInstance().showDialog("Покупка билета", "Успех");
                updatePerformanceList();
                updateBoughtTicketsList();
            } catch (IllegalStateException e) {
                ScreenHelper.getInstance().showError("Ошибка покупки", e.getMessage());
            }
        });
    }

    private void initBoughtTicketsList() {
        updateBoughtTicketsList();
    }

    private void updateBoughtTicketsList() {
        performanceWithBoughtTicketList.getItems().setAll(ticketModel.getBoughtTicketsForCurrentUser());
    }

    private void updatePerformanceList() {
        performanceList.getItems().setAll(performanceModel.getAllUpcomingPerformancesWithAvailableTickets());
    }

    private void updatePerformanceList(long dateAfter) {
        performanceList.getItems().setAll(performanceModel.getAllUpcomingPerformancesWithAvailableTicketsAtDate(dateAfter));
    }

    private void setSitChoiceBoxValues(Performance performance) {
        if (performance != null) sitChoiceBox.getItems().setAll(ticketModel.getAvailableSitsForPerformance(performance));
    }
}
