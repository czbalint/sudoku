package sudoku.Board;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import sudoku.Board.Generator.Generator;
import sudoku.Board.Tools.*;
import sudoku.gameController;
import sudoku.IO.serialLoaderType;
import sudoku.Menu.mainMenuController;

import java.io.IOException;

/**
 * A játékteret vezérlő osztály
 */
public class boardController{

    public GridPane mainBoard;
    public Button penTool;
    public Button eraseTool;
    public Button noteTool;
    public Button helpTool;
    public GridPane numBox;
    public Button pauseButton;
    public Region veil;
    public Label timerLabel;
    public Label hintLabel;
    public MenuItem closeMenu;

    private Tools currentTool;
    private Stage currentStage;
    private final int size;
    private boardFieldPane curPan = null;
    private Button selectedButton;
    private Integer time;
    private Timeline timeline;
    private gameController gameController;
    private int row;
    private int column;
    private final serialLoaderType loaded;
    private int hintCount;
    private boolean isLoaded;
    private  String name;

    /**
     * Egy kapott méret alapján létrehoz egy új játékot
     * @param size kapott méret
     */
    public boardController(int size){
        this.size = size;
        this.loaded = null;
        this.time = 0;
        this.hintCount = 5;
        this.name = null;
        load();
        isLoaded = false;
        setTable();
    }

    /**
     * Egy betöltött tábla alapján hozza létre a játékteret
     * @param load betöltött tábla
     */
    public boardController(serialLoaderType load){
        this.loaded = load;
        this.size = load.getSize();
        this.time = load.getTime();
        this.hintCount = load.getHintCount();
        this.name = load.getName();
        load();
        if (size == 9)
            hintLabel.setText("Segítségek: " + hintCount);
        isLoaded = true;
        setTable();
    }

    /**
     * Az ablakot megjelenítő és inicializáló metódus
     */
    private void load(){
        FXMLLoader loader = null;
        if (size == 9) {
        loader = new FXMLLoader(getClass().getResource("board9.fxml"));
       } else
        if (size == 6) {
            loader = new FXMLLoader(getClass().getResource("board6.fxml"));
       } else
       if (size == 4){
           loader = new FXMLLoader(getClass().getResource("board4.fxml"));
       }

        assert loader != null;
        loader.setControllerFactory(c -> this);
        try {
            Parent main = loader.load();
            Scene root = new Scene(main);
            currentStage = new Stage();
            veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
            veil.setVisible(false);
            currentStage.setResizable(false);
            if (loaded == null)
                currentStage.setTitle("Sudoku - Új játék");
            else
                currentStage.setTitle(name);
            currentStage.setScene(root);
            currentStage.setOnCloseRequest(Event::consume);
            currentStage.setResizable(false);
            currentStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
        currentTool = new penTool(numBox,this);
        penTool.setDisable(true);
        selectedButton = penTool;
        timerLabel.setText("Eltelt idő: ");
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                    (actionEvent) -> {
                        time++;
                        timerLabel.setText("Eltelt idő: " + (time/60) + ":" + (time-((time/60)*60)));
                    }
                ));
        timeline.playFromStart();

    }

    /**
     * Egy a több dimenziós tömbbe másol egy b többdimenziós tömböt
     */
    private void equalizer(int[][] a, int[][] b){
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                a[i][j] = b[i][j];
    }

    /**
     * Legenerálja a táblát és létrehoz/betölti a számokat bele
     */
    private void setTable(){
        ObservableList<Node> v = mainBoard.getChildren();
        int[][] temp;
        int[][] sudokuFull = new int[size][size];
        int[][] sudokuMiss = new int[size][size];
        int[][] sudokuOrigMiss = new int[size][size];
        if (!isLoaded) {
            GridPane sample = (GridPane) v.get(0);
            Generator table = new Generator(size, sample.getColumnCount(), sample.getRowCount());
            table.fillValues();
            temp = table.getMat();
            equalizer(sudokuFull, temp);
            //table.removeKDigits( (size * size) / 2);
            table.removeKDigits(60);
            temp = table.getMat();
            equalizer(sudokuMiss, temp);
            gameController = new gameController(sudokuFull,sudokuMiss,currentStage,size);
        } else {
            equalizer(sudokuFull, loaded.getFull());
            equalizer(sudokuMiss, loaded.getMiss());
            equalizer(sudokuOrigMiss, loaded.getOrigMiss());
            gameController = new gameController(sudokuFull,sudokuMiss,sudokuOrigMiss,currentStage,size,name);
        }
        gameController.print();

        for (int k = 0; k< v.size()-1; k++) {
            Node node = v.get(k);
            Integer i = GridPane.getColumnIndex(node);
            Integer j = GridPane.getRowIndex(node);
            GridPane t = (GridPane) node;
            if (i == null) i = 0;
            if (j == null) j = 0;
            for (int i1 = 0; i1 < t.getColumnCount(); i1++) {
                for (int j1 = 0; j1 < t.getRowCount(); j1++) {
                    boardFieldPane panel;
                    if (!isLoaded) {
                        String num = String.valueOf(sudokuMiss[((t.getRowCount() * j) + j1)][((t.getColumnCount() * i) + i1)]);
                        panel = new boardFieldPane(sudokuMiss[((t.getRowCount() * j) + j1)][((t.getColumnCount() * i) + i1)] == 0 ? "" : num);
                    } else {
                        String num = String.valueOf(sudokuOrigMiss[((t.getRowCount() * j) + j1)][((t.getColumnCount() * i) + i1)]);
                        panel = new boardFieldPane(sudokuOrigMiss[((t.getRowCount() * j) + j1)][((t.getColumnCount() * i) + i1)] == 0 ? "" : num);
                        num = String.valueOf(sudokuMiss[((t.getRowCount() * j) + j1)][((t.getColumnCount() * i) + i1)]);
                        panel.setTextRoot(num.equals("0") ? "" : num);
                    }
                    panel.setOnMouseClicked(this::mouseClickPane);
                    panel.setOnMouseEntered(this::mouseEnteredPane);
                    panel.setOnMouseExited(this::mouseExitPane);
                    panel.setStyle("-fx-background-color: #ffffff");
                    t.add(panel, i1, j1);
                }
            }
        }
    }


    @FXML
    public void mouseExitPane(MouseEvent mouseEvent) {
        Pane pane = (Pane) mouseEvent.getSource();
        pane.setStyle("-fx-background-color: #ffffff");
    }

    @FXML
    public void mouseEnteredPane(MouseEvent e) {
        Pane pane = (Pane) e.getSource();
        pane.setStyle("-fx-background-color: #dedede");
    }

    /**
     * A teljes tábla click eventje
     * @param e
     */
    public void mouseClick(MouseEvent e) {
        Node source = (Node) e.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        if (colIndex == null) colIndex = 0;
        if (rowIndex == null) rowIndex = 0;
        row = rowIndex;
        column = colIndex;
        if (selectedButton == helpTool && hintCount > 0) makeHint(curPan);
        if (selectedButton == eraseTool ) currentTool.upgradePanel();
    }

    /**
     * Adott cellák click eventje
     * @param e
     */
    public void mouseClickPane(MouseEvent e) {
        Node source = (Node)e.getSource() ;
        boardFieldPane panel = (boardFieldPane) source;
        panel.setStyle("-fx-background-color: #808080");
        if (curPan != null){
            curPan.setOnMouseExited(this::mouseExitPane);
            curPan.setOnMouseEntered(this::mouseEnteredPane);
            curPan.setStyle("-fx-background-color: #ffffff");
        }
        panel.setOnMouseExited(null);
        panel.setOnMouseEntered(null);
        if (curPan != panel) {
            curPan = panel;
            currentTool.upgradeCurrent(panel);
            if (selectedButton == noteTool) currentTool.upgradePanel(new Label("-1"));
        }
        else{
            currentTool.upgradeCurrent(null);
            curPan = null;
            if (selectedButton == noteTool) {
                ObservableList<Node> list = numBox.getChildren();
                for (int i=0; i< size;i++){ //feherre allit
                    Pane p = (Pane) list.get(i);
                    p.setStyle("-fx-background-color: #ffffff");
                    p.setOnMouseExited(this::mouseExitPane);
                    p.setOnMouseEntered(this::mouseEnteredPane);
                    p.setOnMouseClicked(this::mouseClickNum);
                }
            }
        }
    }

    /**
     * A számokat tartalmazó doboz click eventje
     * @param e
     */
    public void mouseClickNum(MouseEvent e) {
        Pane pane = (Pane) e.getSource();
        ObservableList<Node> list = pane.getChildren();
        Label l = (Label) list.get(0);
        currentTool.upgradePanel(l);
    }


    public void noteOffClick(MouseEvent e){
        Pane pane = (Pane) e.getSource();
        currentTool.noteOff(pane);
    }

    /**
     * Létrehoz egy új kiválasztott eszközt
     * @param mouseEvent
     */
    public void toolSelect(MouseEvent mouseEvent) {
       if (curPan != null) {
            curPan.setStyle("-fx-background-color: #ffffff");
            curPan.setOnMouseExited(this::mouseExitPane);
            curPan.setOnMouseEntered(this::mouseEnteredPane);
            curPan = null; //itt valami szar van
        }

        if (selectedButton != null)
            selectedButton.setDisable(false);
        selectedButton = (Button) mouseEvent.getSource();
        if (selectedButton == penTool){
            currentTool = new penTool(numBox,this);
        } else
        if (selectedButton == eraseTool){
            currentTool = new eraseTool(numBox,this);
        } else
        if (selectedButton == noteTool){
            currentTool = new noteTool(numBox,this);
        } else
        if (selectedButton == helpTool){
            currentTool = new helpTool(numBox,this);
        } else currentTool = null;

        selectedButton.setDisable(true);

    }

    /**
     * Gombok EventHandlerje
     * A close bezárja a jétékteret és visszalép a főmenübe
     * @param event
     */
    public void buttonClick(ActionEvent event) {
        if (event.getSource() == closeMenu) {
            currentStage.close();
            new mainMenuController();
        } else
        if (event.getSource() == pauseButton) {
            veil.setVisible(true);
            new pauseMenuController(currentStage, veil, this);
            timeline.stop();
        }
    }

    /**
     * Elindítja az időzítőt
     */
    public void timerStart(){
        timeline.play();
    }
    public void save(String name) {gameController.save(time,name,hintCount);}

    /**
     * Ellenörzi hogy kész e a játék és minden szám helyesen van kitöltve
     * @param pane
     */
    public void makeBoardCheck(boardFieldPane pane){
        int rowCount = mainBoard.getColumnCount();
        int columnCount = mainBoard.getRowCount();
        int rowIndex = GridPane.getRowIndex(pane) + row * rowCount;
        int colIndex = GridPane.getColumnIndex(pane) + column * columnCount;
        gameController.tableCheck(rowIndex,colIndex,pane.getTextInt(),time);
    }

    /**
     * A segítségeket vezérli
     * @param pane
     */
    public void makeHint(boardFieldPane pane){
        int rowCount = mainBoard.getColumnCount();
        int columnCount = mainBoard.getRowCount();
        int rowIndex = GridPane.getRowIndex(pane) + row * rowCount;
        int colIndex = GridPane.getColumnIndex(pane) + column * columnCount;
        int num = gameController.getNumber(rowIndex,colIndex);
        System.out.println(""+num);
        if (num != -1){
            pane.setText(""+num);
            hintCount--;
            hintLabel.setText("Segítségek: "+hintCount);
        } else System.out.println("ez alap mezo");
        makeBoardCheck(curPan);
    }

    public int getSize() {
        return size;
    }

    public Integer getTime() {
        return time;
    }
}
