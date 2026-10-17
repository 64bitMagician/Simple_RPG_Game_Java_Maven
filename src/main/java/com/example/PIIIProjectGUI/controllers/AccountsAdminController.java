package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.database.AccountsDAO;
import com.example.PIIIProjectGUI.implementation.classes.database.Account;
import com.example.PIIIProjectGUI.utilities.ViewNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AccountsAdminController {

    @FXML private TableView<Account> accountsTable;
    @FXML private TableColumn<Account, Integer> idColumn;
    @FXML private TableColumn<Account, String> emailColumn;
    @FXML private TableColumn<Account, String> roleColumn;
    @FXML private TableColumn<Account, Void> actionColumn;

    @FXML private Button refreshButton;
    @FXML private Button logoutButton;

    private ObservableList<Account> accountsData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Add delete button column
        addDeleteButtonToTable();

        // Load accounts initially
        loadAccounts();

        // Refresh Button
        refreshButton.setOnAction(e -> loadAccounts());

        // Logout Button
        logoutButton.setOnAction(e -> {
            ViewNavigator.replaceAllStageWindowsWithASingleOne("login-view.fxml","Login");
        });
    }

    private void loadAccounts() {
        accountsData.setAll(AccountsDAO.getAllAccounts());
        accountsTable.setItems(accountsData);
    }

    private void addDeleteButtonToTable() {
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("🗑");

            {
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteBtn.setOnAction(event -> {
                    Account account = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Deletion");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to permanently delete account: " + account.getEmail() + "?");

                    if (alert.showAndWait().filter(btn -> btn == ButtonType.OK).isPresent()) {
                        boolean success = AccountsDAO.deleteAccount(account.getId());
                        if (success) {
                            getTableView().getItems().remove(account);
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Failed to delete account!").showAndWait();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });
    }
}
