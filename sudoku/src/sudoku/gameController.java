package sudoku;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sudoku.IO.saveSudoku;
import sudoku.IO.savesArray;
import sudoku.IO.serialLoaderType;
import sudoku.Board.saveMenuController;

import java.io.*;

/**
 * gameController osztály
 */
public class gameController implements Serializable {

    private final int[][] fullBoard;
    private final int[][] missingBoard;
    private final int[][] origMissingBoard;
    private final Stage bordStage;
    private final int size;
    private final boolean isLoaded;
    private final String name;

    /**
     * Controller konstruktora egy új játék létrehozásakor
     * @param full teljes tábla
     * @param miss hiányos tábla
     * @param board a board Stage
     * @param size tábla mérete
     */
    public gameController(int[][] full, int[][] miss, Stage board, int size){
        this.fullBoard = full;
        this.missingBoard = miss;
        this.bordStage = board;
        this.size = size;
        this.name = null;
        origMissingBoard = new int[size][size];
        equalizer(origMissingBoard,miss);
        isLoaded = false;
    }

    /**
     * Konstruktor létrehozása betöltött táblából
     * @param full teljes tábla
     * @param miss hiányos tábla
     * @param orig eredeti hányos tábla
     * @param board a tábla Stage
     * @param size a tábla mérete
     * @param name a tábla neve
     */
    public gameController(int[][] full, int[][] miss, int[][] orig, Stage board, int size, String name){
        this.fullBoard = full;
        this.missingBoard = miss;
        this.bordStage = board;
        this.size = size;
        this.origMissingBoard = orig;
        this.name = name;
        isLoaded = true;
    }

    private void equalizer(int[][] a, int[][] b){
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                a[i][j] = b[i][j];
    }

    /**
     * megnézi hogy a hiányos tábla egyezik e az eredeti táblával
     * @return
     */
    private boolean equal(){
        for (int i = 0; i < fullBoard.length ; i++) {
            for (int j = 0; j < fullBoard.length; j++) {
                if (fullBoard[i][j] != missingBoard[i][j]) {
                    System.out.println(fullBoard[i][j] + "--" + missingBoard[i][j]);
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * A játék mentését indítja el
     * @param time
     * @param name
     * @param hintCount
     */
    public void save(int time, String name, int hintCount){
        new saveSudoku(new serialLoaderType(size,time,fullBoard,missingBoard,origMissingBoard,name,hintCount),name);
    }

    /**
     * kiírja a konzolra a táblát
     */
    public void print(){
        for (int[] ints : fullBoard) {
            for (int j = 0; j < fullBoard.length; j++)
                System.out.print(ints[j]);
            System.out.println();
        }
    }

    /**
     * A táblák egyezését nézi ha változás van a számokban
     * @param x szám x koordinátája
     * @param y szám y koordinátája
     * @param num az adott szám
     * @param time táblaidő
     */
    public void tableCheck(int x, int y, int num, int time){
        missingBoard[x][y] = num;
        if (equal()) {
            System.out.println("Egyezes");
            bordStage.close();
            if (isLoaded)
                removeSave();
            winAlert();
            new saveMenuController(time,size);
        }
        else System.out.println("Nincs kesz");
    }

    /**
     * Visszaad egy x, y koordinátán lévő számot
     * @param x
     * @param y
     * @return
     */
    public int getNumber(int x, int y){
        if (origMissingBoard[x][y] == 0) return fullBoard[x][y];
        return -1;
    }

    /**
     * Nyerés boxa
     */
    private void winAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nyertél!!!");
        alert.setHeaderText(null);
        alert.setContentText("Teljesítetted a rejtvényt és beírhatod magad az eredménytáblába");
        alert.showAndWait();
    }

    /**
     * Törli a mentést ha sikeresen teljesítunk egy betöltött pályát
     */
    private void removeSave(){
        try {
            ObjectInputStream loader = new ObjectInputStream(new FileInputStream("savesSample"));
            savesArray array = (savesArray) loader.readObject();
            array.remove(name);
            loader.close();
            ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("savesSample"));
            save.writeObject(array);
            save.close();
            File temp = new File("saves/"+name);
            temp.delete();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
