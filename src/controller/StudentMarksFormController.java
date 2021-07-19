package controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SubjectMarks;

import java.math.BigDecimal;

public class StudentMarksFormController {
    public TextField txtId;
    public TextField txtName;
    public TableView<SubjectMarks> tblMarks;
    public TextField txtSubject;
    public TextField txtMarks;
    public Button btnAdd;
    public Button btnRemove;
    public Button btnPrintReport;
    public Button btnExportReport;

    public void initialize() {
        btnAdd.setDisable(true);
        btnRemove.setDisable(true);
        btnExportReport.setDisable(true);
        btnPrintReport.setDisable(true);

        tblMarks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("subject"));
        tblMarks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("marks"));
        TableColumn<SubjectMarks, String> colGrade = (TableColumn<SubjectMarks, String>) tblMarks.getColumns().get(2);

        colGrade.setCellValueFactory(param -> {
            BigDecimal marks = param.getValue().getMarks();
            String grade = "W";

            if (marks.compareTo(new BigDecimal("75")) >= 0) {
                grade = "A";
            } else if (marks.compareTo(new BigDecimal("65")) >= 0) {
                grade = "B";
            } else if (marks.compareTo(new BigDecimal("55")) >= 0) {
                grade = "C";
            } else if (marks.compareTo(new BigDecimal("45")) >= 0) {
                grade = "S";
            }
            return new ReadOnlyStringWrapper(grade);
        });

        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            btnAdd.setDisable(txtSubject.getText().trim().isEmpty() || txtMarks.getText().trim().isEmpty() ||
                    Double.parseDouble(txtMarks.getText()) > 100);
        };

        // Number only text field
        txtMarks.setTextFormatter(new TextFormatter<Object>(change -> {
            if (change.getControlNewText().matches("^\\d*([.]\\d*)?$")) {
                return change;
            }

            return null;
        }));

        tblMarks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnRemove.setDisable(newValue == null);

            if (newValue != null) {
                txtSubject.setText(newValue.getSubject());
                txtMarks.setText(newValue.getMarks().toString());
                btnAdd.setText("Update");
            }
        });

        ChangeListener<String> idTextTableListener = (observable, oldValue, newValue) -> {
            enableExportAndPrint();
        };

        tblMarks.getItems().addListener((ListChangeListener<SubjectMarks>) c -> {
            enableExportAndPrint();
        });
        txtId.textProperty().addListener(idTextTableListener);
        txtName.textProperty().addListener(idTextTableListener);
        txtSubject.textProperty().addListener(changeListener);
        txtMarks.textProperty().addListener(changeListener);
        txtMarks.setOnAction(this::btnAdd_OnAction);
    }

    private void enableExportAndPrint(){
        boolean disable = (txtId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty() || tblMarks.getItems().size() == 0);
        btnExportReport.setDisable(disable);
        btnPrintReport.setDisable(disable);
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        ObservableList<SubjectMarks> subjectMarks = tblMarks.getItems();

        if (btnAdd.getText().equals("Add")) {
            subjectMarks.add(new SubjectMarks(txtSubject.getText(), new BigDecimal(txtMarks.getText())));
        } else {
            SubjectMarks selectedRow = tblMarks.getSelectionModel().getSelectedItem();
            selectedRow.setSubject(txtSubject.getText());
            selectedRow.setMarks(new BigDecimal(txtMarks.getText()));
            tblMarks.refresh();
            tblMarks.getSelectionModel().clearSelection();
        }

        btnAdd.setText("Add");
        txtSubject.clear();
        txtMarks.clear();
        txtSubject.requestFocus();

    }

    public void btnRemove_OnAction(ActionEvent actionEvent) {
        SubjectMarks selectedRow = tblMarks.getSelectionModel().getSelectedItem();
        tblMarks.getItems().remove(selectedRow);
        txtSubject.clear();
        txtMarks.clear();
        tblMarks.getSelectionModel().clearSelection();
        btnAdd.setText("Add");
        txtSubject.requestFocus();
    }

    public void btnPrintReport_OnAction(ActionEvent actionEvent) {
    }

    public void btnExportReport_OnAction(ActionEvent actionEvent) {
    }
}
