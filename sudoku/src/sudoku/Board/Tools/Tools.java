package sudoku.Board.Tools;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sudoku.Board.*;

import java.io.Serializable;

/**
 * Az eszközök ősosztálya
 */
public abstract class Tools implements Serializable {

    protected boardFieldPane currentPane;
    protected Label currentNumber = null;
    protected GridPane numBox;
    protected boardController ctr;
    protected int size;

    protected Tools() {
    }

    /**
     * Frissíti az aktív panelt
     * @param newCurrent
     */
    public void upgradeCurrent(boardFieldPane newCurrent){
        currentPane = newCurrent;
    };

    /**
     * Az aktív panelen műveletet hajt végre egy kapott szám segítségével és meghívja az adott példány edit metódusát
     * @param newNumber
     */
    public void upgradePanel(Label newNumber){
        if (currentPane != null) {
            currentNumber = newNumber;
            edit();
        }
    }

    /**
     * Az aktív panelen műveletet hajt végre és meghíja az adott példány edit metódusát
     */
    public void upgradePanel(){
        if (currentPane != null)
            edit();
    }

    protected void edit() {}
    public void noteOff(Pane pane) {}

    /**
     * Az aktív panelra rárakja a mouseActionokat
     * @param p
     */
    protected void setPaneAction(Pane p){
        p.setOnMouseExited(ctr::mouseExitPane);
        p.setOnMouseEntered(ctr::mouseEnteredPane);
    }

    /**
     * Az aktív panelről leveszi a mouseActionokat
     * @param p
     */
    protected void removePaneAction(Pane p){
        p.setOnMouseExited(null);
        p.setOnMouseEntered(null);
    }
}
