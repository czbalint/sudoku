package sudoku.Board.Tools;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sudoku.Board.*;

/**
 * író eszköz
 */
public class penTool extends Tools{

   public penTool(GridPane numBox, boardController ctr) {
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
     * A panel fő számát lehet vele editelni
     */
    @Override
    protected void edit() {
        currentPane.setText(currentNumber.getText());
        ctr.makeBoardCheck(currentPane);
    }
}
