package sudoku.IO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Listák mentésére használatos osztály
 */
public class savesArray implements Serializable {

    private final ArrayList<aRS> data;

    public savesArray(){
        data = new ArrayList<>();
    }

    /**
     * Új elemet ad a listához
     * @param name
     * @param size
     * @param time
     */
     public void add(String name, int size, int time){
        data.add(new aRS(name,size,time));
     }

    /**
     * Visszaadja a lista indexedik elemét
     * @param index
     * @return
     */
    public String getName(int index) {
        return data.get(index).getName();
    }

    /**
     * Visszaadja a lista indexedik méretét
     * @param index
     * @return
     */
    public int getSize(int index){
        return data.get(index).getSize();
    }

    /**
     * Visszaadja a lista indexedik idejét
     * @param index
     * @return
     */
    public int getTime(int index){
        return data.get(index).getTime();
    }

    /**
     * Visszaadja a lista méretét
     * @return
     */
    public int getArraySize(){
        return data.size();
    }

    /**
     * Megkeres és kitöröl a listából egy teljes adott nevű elemet
     * @param name
     */
    public void remove(String name){
        data.removeIf(temp -> temp.getName().equals(name));
    }

    /**
     * A listában tárolt elemek struktúrája
     */
    private class aRS implements Serializable{
        private String name;
        private int size;
        private int time;

        public aRS(String name, int size, int time){
            this.name = name;
            this.size = size;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }

        public int getTime() {
            return time;
        }
    }

}
