package sample;


import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.sql.*;

public class Controller {
    public Button queryButton;
    public TableView queryResultTable;

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
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM equipment");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
                    if (i > 1) System.out.print(", ");
                    System.out.print(resultSet.getString(i));
                }
                System.out.println();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
