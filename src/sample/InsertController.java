package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.*;

public class InsertController {
    public ChoiceBox tableName;
    public TextField username;
    public PasswordField password;
    public Button queryButton;
    public Pane customerPane;
    public Pane equipmentPane;
    public Pane expensesPane;
    public Pane membershipPlansPane;
    public Pane staffPane;



    public void showQuery() {
        String pane = getCurrentlyVisiblePane();
        if (pane.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Table Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a table to insert into!");
            alert.showAndWait();
            return;
        }
    }

    private String getCurrentlyVisiblePane() {
        if (customerPane.isVisible()) {
            return "customerPane";
        } else if (equipmentPane.isVisible()) {
            return "equipmentPane";
        } else if (expensesPane.isVisible()) {
            return "expensesPane";
        } else if (membershipPlansPane.isVisible()) {
            return "membershipPlansPane";
        } else if (staffPane.isVisible()) {
            return "staffPane";
        }
        return "";
    }

    public void evaluateQuery() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gym_dbms", "root", ""
            );
            Statement stmt = con.createStatement();;

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProperPane(ActionEvent actionEvent) {
        String tableNameSelected = (String) tableName.getValue();
//        System.out.println(tableNameSelected);
        switch (tableNameSelected) {
            case "customer":
                customerPane.setVisible(true);
                expensesPane.setVisible(false);
                membershipPlansPane.setVisible(false);
                equipmentPane.setVisible(false);
                staffPane.setVisible(false);
                break;
            case "expenses":
                customerPane.setVisible(false);
                expensesPane.setVisible(true);
                membershipPlansPane.setVisible(false);
                equipmentPane.setVisible(false);
                staffPane.setVisible(false);
                break;
            case "membership_plans":
                customerPane.setVisible(false);
                expensesPane.setVisible(false);
                membershipPlansPane.setVisible(true);
                equipmentPane.setVisible(false);
                staffPane.setVisible(false);
                break;
            case "equipment":
                customerPane.setVisible(false);
                expensesPane.setVisible(false);
                membershipPlansPane.setVisible(false);
                equipmentPane.setVisible(true);
                staffPane.setVisible(false);
                break;
            case "staff":
                customerPane.setVisible(false);
                expensesPane.setVisible(false);
                membershipPlansPane.setVisible(false);
                equipmentPane.setVisible(false);
                staffPane.setVisible(true);
                break;
        }
    }
}
