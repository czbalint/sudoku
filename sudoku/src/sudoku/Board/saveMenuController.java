package sudoku.Board;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sudoku.Menu.mainMenuController;
import sudoku.IO.saveScore;

import java.io.IOException;

/**
 * A mentés ablakot vezérli
 */
public class saveMenuController {

    Stage currentStage;

    public Button saveButton;
    public TextField nameBox;
    public Label timeLabel;
    public Label sizeLabel;
    public Button backButton;

    private final pauseMenuController ps;
    private final int time;
    private final int size;

    /**
     * Létrhozza az ablakot égy meglévő Stagre
     * @param stage Az előző Stage
     * @param ps az előző ablak vezérlője
     */
    public saveMenuController(Stage stage, pauseMenuController ps){
        this.time = ps.ctr.getTime();
        this.size = ps.ctr.getSize();
        this.currentStage = stage;
        this.ps = ps;
        load();
    }

    /**
     * Létrehoz egy telejsen új ablakot
     * @param time megjelenő idő
     * @param size megjelenő méret
     */
    public saveMenuController(int time, int size){
        this.time = time;
        this.size = size;
        this.currentStage = new Stage();
        load();
        backButton.setVisible(false);
        this.ps = null;
    }

    /**
     * Betölti az ablakot
     */
    private void load() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("saveMenu.fxml"));
        loader.setControllerFactory(c -> this);
        try {
            Parent main = loader.load();
            Scene root = new Scene(main);
            currentStage.setTitle("Sudoku - Mentés");
            currentStage.setScene(root);
            currentStage.setResizable(false);
            currentStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
        sizeLabel.setText("Méret: " + size + "x" + size);
        timeLabel.setText("idő: " + (time/60) + ":" + (time-((time/60)*60)));
    }

    /**
     * A gombok EventHandlerje
     * A vissza gomb meghívja a pauseMenu load függvényét
     * A mentés gomb elidítja a tábla mentő függvényét
     * @param event
     */
    public void buttonAction(ActionEvent event) {
        if (event.getSource() == backButton){
            ps.load(currentStage);
        } else
        if (event.getSource() == saveButton){
            if (!nameBox.getText().equals("")) {
                if (ps != null) {
                    ps.ctr.save(nameBox.getText());
                    ps.bordStage.close();
                    currentStage.close();
                    new mainMenuController();
                } else {
                    new saveScore(nameBox.getText(),size,time);
                    currentStage.close();
                }
            }
        }
    }


}
