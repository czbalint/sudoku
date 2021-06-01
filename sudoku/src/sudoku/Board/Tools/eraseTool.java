package sudoku.Board.Tools;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sudoku.Board.*;

/**
 * Radír eszköz
 */
public class eraseTool extends Tools{
    public eraseTool(GridPane numBox, boardController ctr) {
        this.numBox = numBox;
        this.ctr = ctr;
        this.size = numBox.getColumnCount();
        ObservableList<Node> list = numBox.getChildren();
        for (int i = 0; i < size; i++) {
            Pane p = (Pane) list.get(i);
            p.setStyle("-fx-background-color: #ffffff");
            p.setOnMouseExited(ctr::mouseExitPane);
            p.setOnMouseEntered(ctr::mouseEnteredPane);
            p.setOnMouseClicked(ctr::mouseClickNum);
        }
    }

    /**
     * Törli a számot a panelből
     */
    @Override
    protected void edit() {
        currentPane.setText("");
        currentPane.setNoteTextOff();
        ctr.makeBoardCheck(currentPane);
    }
}
