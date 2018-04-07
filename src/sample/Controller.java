package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.*;

public class Controller {
    private ObservableList<ObservableList> data;
    public Button queryButton;
    public TableView queryResultTable;

    public void showQuery() {
        queryButton.setText("Works");
        evaluateQuery();
    }

    public void buildTable(ResultSet rs) {
        try {
            data = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getCatalogName(i));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                queryResultTable.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
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

    public void evaluateQuery() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gym_dbms", "root", ""
            );
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM equipment");
            buildTable(resultSet);
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
