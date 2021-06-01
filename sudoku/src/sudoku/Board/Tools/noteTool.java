package sudoku.Board.Tools;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sudoku.Board.*;

import java.util.List;

/**
 * Jegyzet eszköz
 */
public class noteTool extends Tools {

    public noteTool(GridPane numBox, boardController ctr) {
        this.numBox = numBox;
        this.ctr = ctr;
        this.size = numBox.getColumnCount();
    }

    /**
     * A kapott számot beállítja a panel jegyzetének
     */
    @Override
    protected void edit() {
        int n=Integer.parseInt(currentNumber.getText());
        if (n!=-1)
            currentPane.setNoteText(n-1,true);
        numberColor();
    }

    /**
     * Kikapcsolja a panel adott jegyzetét
     * @param pane
     */
    @Override
    public void noteOff(Pane pane) {
        ObservableList<Node> list = pane.getChildren();
        Label l = (Label) list.get(0);
        int n = Integer.parseInt(l.getText());
        currentPane.setNoteText(n-1,false);
        pane.setOnMouseClicked(ctr::mouseClickNum);
        numberColor();
    }

    /**
     * Kiszinezi az adott számok hátterét zöldre amik aktívak a panelen.
     */
    private void numberColor(){
        List<Integer> arl = currentPane.getNoteList();
        ObservableList<Node> list = numBox.getChildren();
        for (int i=0; i< size;i++){ //feherre allit
            Pane p = (Pane) list.get(i);
            p.setStyle("-fx-background-color: #ffffff");
            setPaneAction(p);
            p.setOnMouseClicked(ctr::mouseClickNum);
        }
        for (int i=0; i<arl.size();i++){
            Pane pane = (Pane) list.get((arl.get(i))-1);
            pane.setStyle("-fx-background-color: #99e28c");
            removePaneAction(pane);
            pane.setOnMouseClicked(ctr::noteOffClick);

        }

    }

}
