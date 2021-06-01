package sudoku.IO;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.util.Optional;

import sudoku.Board.saveMenuController;
import sudoku.Menu.mainMenuController;

/**
 * A dicsőségtábát menti ki
 */
public class saveScore {
    private savesArray saves;

    /**
     * Ha nem létezik a lista létrehoz egyet,
     * ha létezik akkor hozzáadja a kapott elemet a listához, ehez először beolvassa.
     * Név egyezőség esetén új nevet lehet megadni, vagy a rendszer egy soron következő számot ír utána.
     * @param name
     * @param size
     * @param time
     */
    public saveScore(String name, int size, int time){
        try {
            ObjectOutputStream out;
            if (new File("highScore").exists()){
                ObjectInputStream loader = new ObjectInputStream(new FileInputStream("highScore"));
                saves = (savesArray) loader.readObject();
                loader.close();
                if (!nameUsed(name)){
                    saves.add(name, size, time);
                    out = new ObjectOutputStream(new FileOutputStream("highScore"));
                    out.writeObject(saves);
                    out.close();
                    new mainMenuController();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Használt név");
                    alert.setHeaderText("Ez a név már foglalt!");
                    alert.setContentText("Szeretnél új nevet megadni?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        new saveMenuController(time,size);
                    } else {
                        int i = 1;
                        while (true) {
                            String newName = name + "(" + i + ")";
                            if (!nameUsed(newName)){
                                saves.add(newName,size,time);
                                out = new ObjectOutputStream(new FileOutputStream("highScore"));
                                out.writeObject(saves);
                                out.close();
                                break;
                            } else i++;
                        }
                        new mainMenuController();
                    }
                }
            } else {
                out = new ObjectOutputStream(new FileOutputStream("highScore"));
                saves = new savesArray();
                saves.add(name,size,time);
                out.writeObject(saves);
                out.close();
                new mainMenuController();
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * A név egyezőséget nézi
     * @param name
     * @return
     */
    private boolean nameUsed(String name){
        for (int i = 0; i < saves.getArraySize();i++){
            if (saves.getName(i).equals(name)) return true;
        }
        return false;
    }
}
