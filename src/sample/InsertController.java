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
        queryButton.setText("Works");
        customerPane.setVisible(false);
        evaluateQuery();
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
        switch (tableNameSelected) {
            
        }
    }
}
