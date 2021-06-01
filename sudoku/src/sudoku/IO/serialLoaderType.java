package sudoku.IO;

import java.io.Serializable;

/**
 * Táblát tároló osztály a mentéshez
 */
public class serialLoaderType implements Serializable {
    private final int[][] full;
    private final int[][] miss;
    private final int[][] origMiss;
    private final int size;
    private final int time;
    private String name;
    private final int hintCount;

    /**
     * Átveszi a konstruktor a tábla fő paramétereit
     * @param size méret
     * @param time idő
     * @param full a teli tábla
     * @param miss a hiányos tábla
     * @param origMiss az eredelieg hiányos tábla
     * @param name a tábla neve
     * @param hintCount segítségek száma
     */
    public serialLoaderType(int size, int time,int[][]full, int[][] miss, int[][] origMiss, String name, int hintCount){
        this.full = full;
        this.miss = miss;
        this.origMiss = origMiss;
        this.size = size;
        this.time = time;
        this.name = name;
        this.hintCount = hintCount;
    }

    public int getSize() {
        return size;
    }

    public int[][] getFull() {
        return full;
    }

    public int[][] getMiss() {
        return miss;
    }

    public int[][] getOrigMiss() {
        return origMiss;
    }

    public int getTime() {
        return time;
    }

    public String getName() { return name; }

    public int getHintCount() { return hintCount; }

    public void setName(String name) {
        this.name = name;
    }
}
