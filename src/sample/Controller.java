package sample;


import javafx.scene.control.*;

import java.sql.*;

public class Controller {
    public Button queryButton;
    public Button parse;
    public TableView queryResultTable;
    public TextField tableName;
    public TextField attributes;
    public TextField condAttribute;
    public TextField condComp;
    public TextField orderingAtt;
    public TextArea queryTextArea;
    public ChoiceBox condRel;
    public ChoiceBox orderOptions;

    public void showQuery() {
        queryButton.setText("Works");
        evaluateQuery();
    }

    public void parseQuery() {

        String[] tables = tableName.getText().toString().split(",");
        String[] columns = attributes.getText().toString().split(",");

        StringBuilder q = new StringBuilder(2048);
        q.append("SELECT ");
        for (int i = 0 ; i < columns.length - 1; ++i) {
            q.append(columns[i].trim() + ", ");
        }
        q.append(columns[columns.length - 1].trim());
        q.append(" FROM ");
        for (int i = 0 ; i < tables.length - 1; ++i) {
            q.append(tables[i].trim() + ", ");
        }
        q.append(tables[tables.length - 1].trim());
        if (condRel.getValue() != null){
            q.append(" WHERE ");
            q.append(condAttribute.getText().toString() + " ");
            if (condRel.getValue() == null) {
                System.out.println("Error");
                System.exit(1);
            }
            if (condRel.getValue().toString().equals("less than")) {
                q.append("< " + condComp.getText().toString());
            }
            if (condRel.getValue().toString().equals("less than equals")) {
                q.append("<= " + condComp.getText().toString());
            }
            if (condRel.getValue().toString().equals("greater than")) {
                q.append("> " + condComp.getText().toString());
            }
            if (condRel.getValue().toString().equals("greater than equals")) {
                q.append(">= " + condComp.getText().toString());
            }
            if (condRel.getValue().toString().equals("equals")) {
                q.append("= " + condComp.getText().toString());
            }
            if (condRel.getValue().toString().equals("not equal")) {
                q.append("!= " + condComp.getText().toString());
            }
            if (condRel.getValue().toString().equals("substring")) {
                q.append("LIKE '%" + condComp.getText().toString() + "%'");
            }
            if (condRel.getValue().toString().equals("wordmatch")) {
                q.append("= " + condComp.toString());
            }
        }
        if (orderOptions.getValue() != null) {
            q.append(" ORDER BY " + orderingAtt.getText().toString());
            if (orderOptions.getValue().toString().equals("Descending")) {
                q.append(" DESC");
            }
        }
        queryTextArea.setText(q.toString());
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
