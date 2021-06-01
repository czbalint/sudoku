package sudoku.Board;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * A tábla egy mezőjét abrázoló osztály,
 * A Pane-ből származik
 */
public class boardFieldPane extends Pane {

    private final Label mainText = new Label();
    private final List<Label> list = new ArrayList<>();
    private final boolean editable;

    /**
     * A kapott értéket állítja be magának.
     * Ha a kapott érték nem üres String akkor nem módosíthatóvá állítja magát
     * Ha a kapott String üres akkor késöbb módosítható
     * @param n
     */
    public boardFieldPane(String n){
        mainText.setText("" + n);
        if (n.equals("")) {
            this.editable = true;
        }
        else {
            this.editable = false;
            mainText.setTextFill(Color.SALMON);
        }
        mainText.setStyle("-fx-font-size: 26");
        mainText.layoutXProperty().bind(this.widthProperty().subtract(mainText.widthProperty()).divide(2));
        mainText.layoutYProperty().bind(this.heightProperty().subtract(mainText.heightProperty()).divide(2));
        this.getChildren().addAll(mainText);
        noteSet();
    }

    /**
     * A jegyzetek Labelet inicializálja
     */
    private void noteSet(){
        for (int i = 0;i<9;i++) {
            Label l = new Label(""/*+(i+1)*/);
            list.add(l);
            this.getChildren().addAll(list.get(i));
        }
        list.get(0).layoutXProperty().bind(this.widthProperty().subtract(list.get(0).widthProperty()).divide(5));
        list.get(1).layoutXProperty().bind(this.widthProperty().subtract(list.get(1).widthProperty()).divide(2));
        list.get(2).layoutXProperty().bind(this.widthProperty().subtract(list.get(2).widthProperty()).divide(1.25));
        list.get(3).layoutXProperty().bind(this.widthProperty().subtract(list.get(3).widthProperty()).divide(1.05));
        list.get(3).layoutYProperty().bind(this.heightProperty().subtract(list.get(3).heightProperty()).divide(3));
        list.get(4).layoutXProperty().bind(this.widthProperty().subtract(list.get(4).widthProperty()).divide(1.05));
        list.get(4).layoutYProperty().bind(this.heightProperty().subtract(list.get(4).heightProperty()).divide(1.25));
        list.get(8).layoutXProperty().bind(this.widthProperty().subtract(list.get(7).widthProperty()).divide(90));
        list.get(8).layoutYProperty().bind(this.heightProperty().subtract(list.get(7).heightProperty()).divide(3));
        list.get(7).layoutXProperty().bind(this.widthProperty().subtract(list.get(8).widthProperty()).divide(90));
        list.get(7).layoutYProperty().bind(this.heightProperty().subtract(list.get(8).heightProperty()).divide(1.25));
        list.get(6).layoutXProperty().bind(this.widthProperty().subtract(list.get(5).widthProperty()).divide(3));
        list.get(6).layoutYProperty().bind(this.heightProperty().subtract(list.get(5).heightProperty()).divide(1));
        list.get(5).layoutYProperty().bind(this.heightProperty().subtract(list.get(6).heightProperty()).divide(1));
        list.get(5).layoutXProperty().bind(this.widthProperty().subtract(list.get(6).widthProperty()).divide(1.5));
    }

    /**
     * Visszaadja a fő számot Stringként
     */
    public String getText(){
        return mainText.getText();
    }

    /**
     * Visszaadja a fő számot int ként
     */
    public int getTextInt() {
        try {
            return Integer.parseInt(mainText.getText());
        } catch (NumberFormatException e){
            return 0;
        }

    }

    /**
     * A fő számot settere
     * @param s
     */
    public void setText(String s){
        System.out.println(editable);
        if (editable)
            mainText.setText(s);
    }

    /**
     * A fő szám settere ha nem módosítható
     * @param s
     */
    public void setTextRoot(String s){
        mainText.setText(s);
    }

    /**
     * Megjelenít egy jegyzetet
     * @param n Adott jegyzet
     * @param state A jegyzet állapota
     */
    public void setNoteText(int n, boolean state) {
        if (editable)
        try {
            if (n < 9) {
                if (state)
                    list.get(n).setText("" + (n + 1));
                else
                    list.get(n).setText("");
            } else
                throw new ArrayIndexOutOfBoundsException("Note tulindexeles");
        } catch (ArrayIndexOutOfBoundsException e){
            e.fillInStackTrace();
        }
    }

    /**
     * Kikapcsol minden jegyzetet
     */
    public void setNoteTextOff(){
        for (Label l: list){
            l.setText("");
        }
    }

    /**
     * Visszaadja az aktí jegyzetek listáját
     * @return
     */
    public List<Integer> getNoteList() {
        List<Integer> n = new ArrayList<>();
        for (Label l : list){
            if (!l.getText().equals("")) {
                int i = Integer.parseInt(l.getText());
                n.add(i);
            }
        }
        return n;
    }
}
