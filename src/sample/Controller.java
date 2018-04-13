package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;

public class Controller {
    private ObservableList<ObservableList> data;
    public Button queryButton;
    public Button parse;
    public TableView queryResultTable;
    public TextArea queryTextArea;
    public TextField tableName;
    public TextField attributes;
    public TextField condAttribute;
    public TextField condComp;
    public TextField orderingAtt;
    public TextField aggreAttribute;
    public TextField groupBy;
    public ChoiceBox aggreDropDown;
    public ChoiceBox condRel;
    public ChoiceBox orderOptions;
    public ChoiceBox multOption;
    public Button nextButton;
    public int intersectFlag = 0;
    public StringBuilder q = new StringBuilder(2048);
    public void showQuery() {
//        queryButton.setText("Works");
        evaluateQuery(queryTextArea.getText());
    }

    public void buildTable(ResultSet rs) {
        try {
            data = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
//                        System.out.println(param.getValue().get(j - 1).toString());
                    return new SimpleStringProperty(param.getValue().get(j - 1).toString());
                });
                queryResultTable.getColumns().addAll(col);
//                System.out.println("Column ["+i+"] ");
            }

            // Add data to observable list
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            queryResultTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseQuery() {

        String[] tables = tableName.getText().split(",");
        String[] columns = attributes.getText().split(",");
        String aggreGrateFunc = (aggreDropDown.getValue() != null) ? aggreDropDown.getValue().toString() : null;
//        System.out.println(aggreGrateFunc);

        q.append("SELECT ");
        for (int i = 0 ; i < columns.length; ++i) {
            if (aggreAttribute.getText().contains(columns[i].trim()) && aggreGrateFunc != null) {
                if (i == columns.length - 1)
                    q.append(aggreGrateFunc).append("(").append(columns[i].trim()).append(")");
                else
                    q.append(aggreGrateFunc).append("(").append(columns[i].trim()).append("), ");
            } else {
                if (i == columns.length - 1)
                    q.append(columns[i].trim());
                else
                    q.append(columns[i].trim()).append(", ");
            }
        }
//        q.append(columns[columns.length - 1].trim());
        q.append(" FROM ");
        for (int i = 0 ; i < tables.length - 1; ++i) {
            q.append(tables[i].trim()).append(", ");
        }
        q.append(tables[tables.length - 1].trim());
        if (condRel.getValue() != null){
            q.append(" WHERE ");
            q.append(condAttribute.getText() + " ");
            if (condRel.getValue() == null) {
                System.out.println("Error");
                System.exit(1);
            }
            if (condRel.getValue().toString().equals("less than")) {
                q.append("< " + condComp.getText());
            }
            else if (condRel.getValue().toString().equals("less than equals")) {
                q.append("<= " + condComp.getText());
            }
            else if (condRel.getValue().toString().equals("greater than")) {
                q.append("> " + condComp.getText());
            }
            else if (condRel.getValue().toString().equals("greater than equals")) {
                q.append(">= " + condComp.getText());
            }
            else if (condRel.getValue().toString().equals("equals")) {
                q.append("= " + condComp.getText());
            }
            else if (condRel.getValue().toString().equals("not equal")) {
                q.append("!= " + condComp.getText());
            }
            else if (condRel.getValue().toString().equals("substring")) {
                q.append("LIKE '%" + condComp.getText() + "%'");
            }
            else if (condRel.getValue().toString().equals("word match")) {
                q.append("= '" + condComp.getText() +"'");
            }
            else if (condRel.getValue().toString().equals("SUM")) {

            }
        }
        if (orderOptions.getValue() != null) {
            q.append(" ORDER BY " + orderingAtt.getText());
            if (orderOptions.getValue().toString().equals("Descending")) {
                q.append(" DESC");
            }
        }
        if (intersectFlag == 1) {
            q.append(")");
            intersectFlag = 0;
        }
        if (!groupBy.getText().equals("")) {
            q.append(" GROUP BY ").append(groupBy.getText());
        }
        queryTextArea.setText(q.toString());
    }

    public void storeResults() {
        parseQuery();
        if (multOption.getValue().toString().compareTo("Union") == 0) {
            q.append("\nUNION\n");
        }
        else if (multOption.getValue().toString().compareTo("Intersect") == 0) {
            if (attributes.getText().split(",").length > 1) {
                System.out.println("error");
                System.exit(1);
            }
            q.append(" WHERE " + attributes.getText().split(",")[0] + " IN (");
            intersectFlag = 1;
        }
        else if (multOption.getValue().toString().compareTo("Except") == 0) {
            q.append(" WHERE " + attributes.getText().split(",")[0] + " NOT IN (");
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
        aggreDropDown.setValue(null);
        aggreAttribute.setText("");
        groupBy.setText("");
    }

    public void evaluateQuery(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gym_dbms", "root", ""
            );
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            queryResultTable.getColumns().clear();
            buildTable(resultSet);
//            ResultSet resultSet = stmt.executeQuery(q.toString());
            ResultSetMetaData rsmd = resultSet.getMetaData();
//            while (resultSet.next()) {
//                for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
//                    if (i > 1) System.out.print(", ");
//                    System.out.print(resultSet.getString(i));
//                }
//                System.out.println();
//            }
            con.close();
            q = new StringBuilder();
            resetAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showAdminPage(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("insert.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Admin Page");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
