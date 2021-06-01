package sudoku.Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * A főmenü megjelenítésére szolgáló osztály
 */
public class mainMenuController{

    private Stage primaryStage;
    
    @FXML
    public Button newButton;
    @FXML
    public Button loadButton;
    @FXML
    public Button highScoreButton;
    @FXML
    public Button exitButton;

    /**
     * Konstruktor egy meglévő Stagere való betöltésre
     * @param primaryStage
     */
    public mainMenuController(Stage primaryStage){
        File file = new File("saves");
        if (file.mkdir()) System.out.println("sikere");
        else System.out.println("nem sikeres");
        this.primaryStage = primaryStage;
        load();
    }

    /**
     * Konstruktor új Stage létrehozásával
     */
    public mainMenuController() {
        primaryStage = new Stage();
        load();
    }

    /**
     * Gombok EventHandler-je
     * @param event
     */
    public void buttonActionEvent(ActionEvent event) {
            if (event.getSource() == newButton){
                newGame();
            } else
            if (event.getSource() == loadButton){
                loadGame();
            } else
            if (event.getSource() == highScoreButton){
                scoreBoard();
            } else
            if (event.getSource() == exitButton){
                exit();
            }
    }

    /**
     * A dicsőséglistát jeleníti meg
     */
    private void scoreBoard() {
        primaryStage.close();
        new scoreMenuController();
    }

    /**
     * A folytatás menüt tölti be
     */
    private void loadGame() {
        primaryStage.close();
        new loadMenuController();
    }

    /**
     * BEzárja a programot
     */
    private void exit() {
        primaryStage.close();
    }

    /**
     * Új játékot hoz létr
     */
    private void newGame(){
        primaryStage.close();
        new newMenuController();
    }

    /**
     * Az ablak betöltő függvénye
     */
    public void load(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        loader.setControllerFactory(c -> this);
        try {
            Parent main = loader.load();
            Scene root = new Scene(main);
            primaryStage.setTitle("Sudoku - Menü");
            primaryStage.setScene(root);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
