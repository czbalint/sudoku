package sudoku.IO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Mentett táblát tölt be fájlból
 */
public class loadSudoku {

    private serialLoaderType load;

    /**
     * Egy adott nevű fájlt betölt és elmeti azt.
     * @param name
     */
    public loadSudoku(String name){
        try {
            ObjectInputStream loader = new ObjectInputStream(new FileInputStream("saves/"+name));
            load = (serialLoaderType) loader.readObject();
            loader.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * A beolvasott típus gettere
     * @return
     */
    public serialLoaderType getLoad() {
        return load;
    }
}
