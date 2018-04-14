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
    public TextField memberName;
    public TextField memberType;
    public Pane equipmentPane;
    public TextField machineName;
    public TextField quantity;
    public CheckBox workingProperly;
    public Pane expensesPane;
    public TextField purpose;
    public TextField amount;
    public Pane membershipPlansPane;
    public TextField cost;
    public TextField duration;
    public TextField trainer;
    public TextField membershipType;
    public Pane staffPane;
    public TextField staffName;
    public TextField staffDivision;
    public TextField staffSalary;
    StringBuilder s = new StringBuilder(1024);
    public TextArea queryTA;


    public void showQuery() {
        if (!username.getText().equals("admin") || !password.getText().equals("admin")) {
            showInvalidUserPassDialog();
            return;
        }
        String pane = getCurrentlyVisiblePane();
        if (pane.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Table Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a table to insert into!");
            alert.showAndWait();
            return;
        }
        s = new StringBuilder(1024);
        s.append("INSERT INTO ");
        if (pane.equals("customerPane")) {
            // INSERT INTO `customer` (`CustomerID`, `Name`, `MembershipType`) VALUES (NULL, 'ljljlkj', 'lkjlkjlk')
            s.append("`customer` (`CustomerID`, `Name`, `MembershipType`) VALUES (NULL, ");
            s.append('\'').append(memberName.getText()).append('\'').append(", ");
            s.append('\'').append(memberType.getText()).append('\'');
            s.append(')');
        } else if (pane.equals("equipmentPane")) {
            // INSERT INTO `equipment` (`MachineID`, `LastMaintenance`, `MachineName`, `Quantity`, `WorkingProperly`) VALUES (NULL, CURRENT_TIMESTAMP, 'poqwri', '12', '1')
            Character workingProp;
            if (workingProperly.isSelected())
                workingProp = '1';
            else
                workingProp = '0';
            s.append("`equipment` (`MachineID`, `LastMaintenance`, `MachineName`, `Quantity`, `WorkingProperly`) VALUES (NULL, CURRENT_TIMESTAMP, ");
            s.append('\'').append(machineName.getText()).append('\'').append(", ");
            s.append('\'').append(quantity.getText()).append('\'').append(", ");
            s.append('\'').append(workingProp).append('\'');
            s.append(')');
        } else if (pane.equals("expensesPane")) {
            // INSERT INTO `expenses` (`ExpenseID`, `Purpose`, `Amount`) VALUES (NULL, 'qpowri', '1000')
            s.append("`expenses` (`ExpenseID`, `Purpose`, `Amount`) VALUES (NULL, ");
            s.append('\'').append(purpose.getText()).append('\'').append(", ");
            s.append('\'').append(amount.getText()).append('\'');
            s.append(')');
        } else if (pane.equals("membershipPlansPane")) {
            // INSERT INTO `membership_plans` (`Cost`, `Duration`, `Trainer`, `MembershipType`) VALUES ('123', '5', 'Rahul', 'Plex')
            s.append("`membership_plans` (`Cost`, `Duration`, `Trainer`, `MembershipType`) VALUES (");
            s.append('\'').append(cost.getText()).append('\'').append(", ");
            s.append('\'').append(duration.getText()).append('\'').append(", ");
            s.append('\'').append(trainer.getText()).append('\'').append(", ");
            s.append('\'').append(membershipType.getText()).append('\'');
            s.append(')');
        } else if (pane.equals("staffPane")) {
            // INSERT INTO `staff` (`EmployeeID`, `Name`, `Division`, `Salary`) VALUES (NULL, 'poiq', 'psdf', '1234.12')
            s.append("`staff` (`EmployeeID`, `Name`, `Division`, `Salary`) VALUES (NULL, ");
            s.append('\'').append(staffName.getText()).append('\'').append(", ");
            s.append('\'').append(staffDivision.getText()).append('\'').append(", ");
            s.append('\'').append(staffSalary.getText()).append('\'');
            s.append(')');
        }

        if (s.equals("INSERT INTO ")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Query Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Query error!");
            alert.showAndWait();
            return;
        }
//        System.out.println(s);
        queryTA.setText(s.toString());

    }

    private void showInvalidUserPassDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Authentication Error");
        alert.setHeaderText(null);
        alert.setContentText("Invalid username or password!");
        alert.showAndWait();
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
        if (!username.getText().equals("admin") || !password.getText().equals("admin")) {
            showInvalidUserPassDialog();
            return;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gym_dbms", "root", ""
            );
            Statement stmt = con.createStatement();
            stmt.executeUpdate(s.toString());
            s = new StringBuilder(1024);
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
