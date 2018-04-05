package sample;


import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class Controller {
    public Button queryButton;
    public TableView queryResultTable;

    public void showQuery() {
        queryButton.setText("Works");
    }
}
