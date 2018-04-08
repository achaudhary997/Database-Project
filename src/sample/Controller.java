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
    public ChoiceBox multOption;
    public Button nextButton;
    public int intersectFlag = 0;
    public StringBuilder q = new StringBuilder(2048);
    public void showQuery() {
        queryButton.setText("Works");
        evaluateQuery();
    }

    public void parseQuery() {

        String[] tables = tableName.getText().toString().split(",");
        String[] columns = attributes.getText().toString().split(",");

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
            if (condRel.getValue().toString().equals("word match")) {
                q.append("= '" + condComp.getText().toString() +"'");
            }
        }
        if (orderOptions.getValue() != null) {
            q.append(" ORDER BY " + orderingAtt.getText().toString());
            if (orderOptions.getValue().toString().equals("Descending")) {
                q.append(" DESC");
            }
        }
        if (intersectFlag == 1) {
            q.append(")");
            intersectFlag = 0;
        }
        queryTextArea.setText(q.toString());
    }

    public void storeResults() {
        parseQuery();
        if (multOption.getValue().toString().compareTo("Union") == 0) {
            q.append("\nUNION\n");
        }
        else if (multOption.getValue().toString().compareTo("Intersect") == 0) {
            if (attributes.getText().toString().split(",").length > 1) {
                System.out.println("error");
                System.exit(1);
            }
            q.append(" WHERE " + attributes.getText().toString().split(",")[0] + " IN (");
            intersectFlag = 1;
        }
        else if (multOption.getValue().toString().compareTo("Except") == 0) {
            q.append(" WHERE " + attributes.getText().toString().split(",")[0] + " NOT IN (");
            intersectFlag = 1;
        }
        resetAll();
    }

    public void newQuery() {
        q = new StringBuilder(2048);
        resetAll();
    }

    public void resetAll() {
        tableName.setText("");
        attributes.setText("");
        condAttribute.setText("");
        condComp.setText("");
        condRel.setValue(null);
        orderingAtt.setText("");
        orderOptions.setValue(null);
        queryTextArea.setText("");
    }

    public void evaluateQuery() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gym_dbms", "root", ""
            );
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(q.toString());
            ResultSetMetaData rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
                    if (i > 1) System.out.print(", ");
                    System.out.print(resultSet.getString(i));
                }
                System.out.println();
            }
            con.close();
            q = new StringBuilder();
            resetAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
