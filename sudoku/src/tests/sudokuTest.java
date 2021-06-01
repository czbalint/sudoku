import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sudoku.Board.Generator.Generator;
import sudoku.IO.*;


import java.io.File;

public class sudokuTest {

    private int[][] fulltable9;
    private int[][] misstable9;
    private int[][] fulltable6;
    private int[][] fulltable4;

    @Before
    public void init(){
        Generator generator = new Generator(9,3,3);
        generator.fillValues();
        fulltable9 = generator.getMat();
        generator.removeKDigits((9*9)/2);
        misstable9 = generator.getMat();
        generator = new Generator(6,3,2);
        fulltable6 = generator.getMat();
        generator = new Generator(4,2,2);
        fulltable4 = generator.getMat();

    }

    @Test
    public void sizeTest(){
        Assert.assertEquals(9,fulltable9.length);
        Assert.assertEquals(9,fulltable9[0].length);
        Assert.assertEquals(6,fulltable6.length);
        Assert.assertEquals(6,fulltable6[0].length);
        Assert.assertEquals(4,fulltable4.length);
        Assert.assertEquals(4,fulltable4[0].length);
    }

    @Test
    public void missingTest(){

        boolean match = true;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++){
                if(misstable9[i][j] != 0 && misstable9[i][j] != fulltable9[i][j]) match = false;
            }
        Assert.assertTrue(match);
    }

    @Test
    public void tableLoad(){
        File f = new File("savesSample");
        f.delete();
        serialLoaderType table = new serialLoaderType(9,10,fulltable9,misstable9,misstable9,"test",5);
        new saveSudoku(table,"test");
        loadSudoku loader = new loadSudoku("test");
        serialLoaderType loadedTAble = loader.getLoad();
        int[][] loaded = loadedTAble.getFull();
        boolean match = true;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++){
                if (fulltable9[i][j] != loaded[i][j]) match = false;
            }
        Assert.assertTrue(match);
    }

}
