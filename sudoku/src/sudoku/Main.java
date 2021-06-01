package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import sudoku.Menu.mainMenuController;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage){
        primaryStage.setResizable(false);
        new mainMenuController(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
