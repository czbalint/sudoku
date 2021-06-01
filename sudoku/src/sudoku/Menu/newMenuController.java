package sudoku.Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.IOException;
import sudoku.Board.boardController;

/**
 * Új játék menü osztálya
 */
public class newMenuController{

    private Stage currentStage;
    private int selectVal;

    @FXML
    public Button startButton;
    @FXML
    public Button backButton;
    @FXML
    public ComboBox<String> comboBox;

    public newMenuController() {
        load();
    }

    /**
     * Az ablak betöltő függvénye
     */
    private void load(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newMenu.fxml"));
        loader.setControllerFactory(c -> this);
        try {
            Parent main = loader.load();
            Scene root = new Scene(main);
            currentStage = new Stage();
            currentStage.setTitle("Sudoku - Új játék");
            currentStage.setScene(root);
            currentStage.setResizable(false);
            currentStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
        comboBox.getItems().addAll("9x9","6x6","4x4");
    }

    /**
     * Gombok EventHandler-je
     * @param event
     */
    public void buttonActionEvent(ActionEvent event) {
        if (event.getSource() == backButton){
            currentStage.close();
            new mainMenuController();
        } else
        if (event.getSource() == startButton) {
            currentStage.close();
            new boardController(selectVal);
        }
    }

    /**
     * comboBox EvenetHandler-je
     * @param event
     */
    public void comboAction(ActionEvent event) {
        if (comboBox.getValue().equals("9x9")){
            selectVal = 9;
            startButton.setDisable(false);
        } else
        if (comboBox.getValue().equals("6x6")){
            selectVal = 6;
            startButton.setDisable(false);
        } else
        if (comboBox.getValue().equals("4x4")){
            selectVal = 4;
            startButton.setDisable(false);
        }

    }
}
