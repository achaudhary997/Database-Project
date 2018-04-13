package sample;

import javafx.scene.control.*;

import java.sql.*;

public class InsertController {
    public ChoiceBox tableName;
    public TextField username;
    public PasswordField password;
    public Button queryButton;

    public void showQuery() {
        queryButton.setText("Works");
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
}
