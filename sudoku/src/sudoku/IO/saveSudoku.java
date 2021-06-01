package sudoku.IO;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.util.Optional;

/**
 * Játéjtáblát menti ki
 */
public class saveSudoku {

    private savesArray saves;

    /**
     *Egy külön fájlba először kimenti a tábla fő tulajdonságait,
     * és itt nézi a név egyezést is.
     * Ha névegyezés van akkor a mentés felülirahato vagy egy számot ír utána a rendszer.
     * Majd a táblát az adott néven a savas mappába menti.
     * @param board A kimeneti kívánt tábla adatosztálya
     * @param fileName adott fájlnév
     */
     public saveSudoku(serialLoaderType board, String fileName){
         try {
             ObjectOutputStream out;
             if (new File("savesSample").exists()){
                 ObjectInputStream loader = new ObjectInputStream(new FileInputStream("savesSample"));
                 saves = (savesArray) loader.readObject();
                 loader.close();
                 if (!nameUsed(fileName)) {
                     saves.add(fileName, board.getSize(), board.getTime());
                     out = new ObjectOutputStream(new FileOutputStream("savesSample"));
                     out.writeObject(saves);
                     out.close();
                     out = new ObjectOutputStream( new FileOutputStream("saves/"+fileName));
                     out.writeObject(board);
                     out.close();
                 } else {
                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                     alert.setTitle("Használt név");
                     alert.setHeaderText("Ez a név már foglalt!");
                     alert.setContentText("Biztos felül szeretnéd írni?");

                     Optional<ButtonType> result = alert.showAndWait();
                     if (result.get() == ButtonType.OK){
                         saves.remove(fileName);
                         saves.add(fileName,board.getSize(),board.getTime());
                         out = new ObjectOutputStream(new FileOutputStream("savesSample"));
                         out.writeObject(saves);
                         out.close();
                         out = new ObjectOutputStream( new FileOutputStream("saves/"+fileName));
                         out.writeObject(board);
                         out.close();
                     } else {
                         int i = 1;
                         while (true) {
                             String newName = fileName + "(" + i + ")";
                             if (!nameUsed(newName)){
                                 saves.add(newName,board.getSize(),board.getTime());
                                 out = new ObjectOutputStream(new FileOutputStream("savesSample"));
                                 out.writeObject(saves);
                                 out.close();
                                 out = new ObjectOutputStream( new FileOutputStream("saves/"+newName));
                                 board.setName(newName);
                                 out.writeObject(board);
                                 out.close();
                                 break;
                             } else i++;
                         }
                     }
                 }

             } else {
                 out = new ObjectOutputStream(new FileOutputStream("savesSample"));
                 saves = new savesArray();
                 saves.add(fileName,board.getSize(),board.getTime());
                 out.writeObject(saves);
                 out.close();
                 out = new ObjectOutputStream( new FileOutputStream("saves/"+fileName));
                 out.writeObject(board);
                 out.close();
             }
         }catch (IOException | ClassNotFoundException e){
             e.printStackTrace();
         }

     }

     private boolean nameUsed(String name){
         for (int i = 0; i < saves.getArraySize();i++){
             if (saves.getName(i).equals(name)) return true;
         }
         return false;
     }
}
