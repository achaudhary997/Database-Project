package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;

public class InsertController {
    public TextField tableName;
    public TextArea insAttributes;
    public TextArea insValues;
    public Button parse;
    public TextArea queryTextArea;
    public Button queryButton;
    StringBuilder q;
    public void showQuery() {
        queryButton.setText("Works");
        evaluateQuery();
    }
    public void parseQuery() {
        q = new StringBuilder(2048);
        q.append("INSERT INTO `");
        q.append(tableName.getText().toString() + "` (");
        String[] columns = insAttributes.getText().toString().split(",");
        for (int i = 0 ; i < columns.length - 1; ++i) {
            q.append("`" + columns[i].trim() + "`, ");
        }
        q.append("`" + columns[columns.length - 1].trim() + "`) VALUES (");
        String[] values = insValues.getText().toString().split(",");
        for (int i = 0 ; i < values.length - 1; ++i) {
            q.append("'" + values[i].trim() + "', ");
        }
        q.append("'" + values[values.length - 1].trim() + "');");
        queryTextArea.setText(q.toString());
    }

    public void evaluateQuery() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gym_dbms", "root", ""
            );
            Statement stmt = con.createStatement();
            System.out.println(q.toString());
            int result = stmt.executeUpdate(q.toString());

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
