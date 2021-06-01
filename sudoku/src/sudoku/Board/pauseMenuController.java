package sudoku.Board;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sudoku.Menu.mainMenuController;

import java.io.IOException;

/**
 * Szünet ablak
 */
public class pauseMenuController {

    private final Stage currentStage;

    public Button pauseButton;
    public Button saveButton;
    public Button exitButton;
    private final Region veil;
    public final Stage bordStage;
    public final boardController ctr;

    /**
     * Letrehozza egy már meglévő Stagre
     * @param stage Adott stage
     * @param veil a táblát eltakaró panel
     * @param ctr a tábla vezérlője
     */
    public pauseMenuController(Stage stage , Region veil, boardController ctr){
        this.veil = veil;
        this.bordStage = stage;
        this.ctr = ctr;
        currentStage = new Stage();
        load(currentStage);
    }

    /**
     * Betölti az ablakot
     * @param stage
     */
    public void load(Stage stage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pauseMenu.fxml"));
        loader.setControllerFactory(c -> this);
        try {
            Parent main = loader.load();
            Scene root = new Scene(main);
            stage.setTitle("Sudoku - Szünet");
            stage.setScene(root);
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    stage.close();
                    veil.setVisible(false);
                    ctr.timerStart();
                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Gombok EventHandler-je
     * A vissza gombal visszaléphetunk a táblára
     * A mentés gombal meghíhatjuk a mentés ablakot
     * A kilépés gombbal kilépünk a játékból
     * @param event
     */
    public void buttonAction(ActionEvent event) {
        if (pauseButton == event.getSource()){
            currentStage.close();
            veil.setVisible(false);
            ctr.timerStart();
        } else
        if (saveButton == event.getSource()){
            //ctr.save();
            new saveMenuController(currentStage,this);
        } else
        if (exitButton == event.getSource()){
            bordStage.close();
            currentStage.close();
            new mainMenuController();
        }
    }
}
