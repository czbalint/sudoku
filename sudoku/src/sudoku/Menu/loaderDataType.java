package sudoku.Menu;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *Adatok mentésére használható osztály, amelyeket a menükben a táblázatokba betöltve lehet megjeleníteni.
 */
public class loaderDataType {
    private final SimpleStringProperty name;
    private final SimpleStringProperty size;
    private final SimpleIntegerProperty time;

    /**
     * Név idő és méret tárolását teszi lehetővé amit konstruktorból állíthatunk
     * @param name
     * @param size
     * @param time
     */
    public loaderDataType(String name, int size, int time){
        this.name = new SimpleStringProperty(name);
        this.size = new SimpleStringProperty("" + size + "x" + size);
        this.time = new SimpleIntegerProperty(time);
    }

    /**
     * Név gettere
     * @return
     */
    public String getName() {
        return name.get();
    }

    /**
     * Méret gettere
     * @return
     */
    public String getSize() {
        return size.get();
    }

    /**
     * Idő gettere String ként formázva
     * @return
     */
    public String getTime() {
        Integer min = time.get()/60;
        Integer sec = time.get() - min * 60;
        System.out.println(min);
        return min.toString() + ":" + sec.toString();
    }

    /**
     * Idő gettere int ként
     * @return
     */
    public int getTimeInt(){
        return time.get();
    }

}
