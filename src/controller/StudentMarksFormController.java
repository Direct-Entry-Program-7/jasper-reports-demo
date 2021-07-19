package controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.SubjectMarks;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

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

            if (marks.compareTo(new BigDecimal("75")) >= 0){
                grade= "A";
            }else if(marks.compareTo(new BigDecimal("65")) >= 0){
                grade= "B";
            }else if(marks.compareTo(new BigDecimal("55")) >= 0){
                grade = "C";
            }else if(marks.compareTo(new BigDecimal("45")) >= 0){
                grade = "S";
            }
            return new ReadOnlyStringWrapper(grade);
        });

        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            if (txtSubject.getText().trim().isEmpty() || txtMarks.getText().trim().isEmpty()  ||
                Double.parseDouble(txtMarks.getText()) > 100) {
                btnAdd.setDisable(true);
            } else {
                btnAdd.setDisable(false);
            }
        };

        // Number only text field
        txtMarks.setTextFormatter(new TextFormatter<Object>(change -> {
            if (change.getControlNewText().matches("^\\d*([.]\\d*)?$")){
                return change;
            }

            return null;
        }));

        txtSubject.textProperty().addListener(changeListener);
        txtMarks.textProperty().addListener(changeListener);
        txtMarks.setOnAction(this::btnAdd_OnAction);
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        ObservableList<SubjectMarks> subjectMarks = tblMarks.getItems();

        subjectMarks.add(new SubjectMarks(txtSubject.getText(), new BigDecimal(txtMarks.getText())));
        txtSubject.clear();
        txtMarks.clear();
        txtSubject.requestFocus();
    }

    public void btnRemove_OnAction(ActionEvent actionEvent) {
    }

    public void btnPrintReport_OnAction(ActionEvent actionEvent) {
    }

    public void btnExportReport_OnAction(ActionEvent actionEvent) {
    }
}
