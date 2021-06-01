package sudoku.Board.Generator;

import java.lang.*;

/**
 * Tábla generátor osztály
 */
public class Generator {
    int[][] mat; //tabla
    int N; //tabla meret
    int columnCount;
    int rowCount;

    /**
     *A konstruktor bekéri a táblaméretet és benne lévő kis tábláknaknak a sor és oszlop számát
     * @param N
     * @param columnCount
     * @param rowColumn
     */
    public Generator(int N, int columnCount, int rowColumn) {
        this.N = N;
        this.columnCount = columnCount;
        this.rowCount = rowColumn;
        mat = new int[N][N];
    }

    /**
     * feltölti a táblát értékekkel
     */
    public void fillValues() {
            if (N == 9){
                fillDiagonal();
                fillRemaining(0, columnCount);
                return;
            }
            boolean fine = false;
            while (!fine) {
                System.out.println("new matrix");
                mat = new int[N][N];
                fillDiagonal();
                fine = fillRemain();
            }
    }

    /**
     * Elsőnek a diagonális mátrixot tölti fel számokkal
     */
    private void fillDiagonal() {
        int j = 0;
        for (int i = 0; i<N; i=i+columnCount) {
            fillBox(j, i);
            j += rowCount;
        }
    }

    /**
     * megnézi hogy egy szám az adott kis táblán belül használva van e
     * @param rowStart A kis tábla első cellájának sora
     * @param colStart A kis tábla első cellájának oszlopa
     * @param num A szám
     * @return Igaz ha nincs benne, hamis ha igen
     */
    private boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i<rowCount; i++)
            for (int j = 0; j<columnCount; j++)
                if (mat[rowStart+i][colStart+j]==num)
                    return false;
        return true;
    }

    /**
     * Feltölt egy kis táblát számokkal
     * @param row kis tábla kezdetének sora
     * @param col kis tábla kezdetének oszlopa
     */
    private void fillBox(int row,int col) {
        int num;
        for (int i=0; i<rowCount; i++){
            for (int j=0; j<columnCount; j++) {
                do {
                    num = randomGenerator(N);
                }
                while (!unUsedInBox(row, col, num));

                mat[row+i][col+j] = num;
            }
        }
    }

    /**
     * Random generál számot
     * @param num
     * @return
     */
     private int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }

    /**
     * Megnézi hogy az adott szám a szabályoknak megfelelő-e
     * @param i
     * @param j
     * @param num
     * @return
     */
    private boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%rowCount, j-j%columnCount, num));
    }

    /**
     * Megnézi hogy az adott szám használható e az adott sorban
     * @param i
     * @param num
     * @return
     */
    private boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    /**
     * Megnézi hogy az adott szám használható e az adott oszlopban
     * @param j
     * @param num
     * @return
     */
    boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    /**
     * Feltölti a maradék üres helyeket rekurzívan a 9x9 es táblához
     * @param i
     * @param j
     * @return
     */
    boolean fillRemaining(int i, int j)
    {
        if (j>=N && i<N-1) {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;

        if (i < rowCount) {
            if (j < columnCount)
                j = columnCount;
        } else
            if (i < N-rowCount){
                if (j==(i/rowCount)*rowCount)
                    j =  j + columnCount;
            } else
                if (j == N-columnCount)
                {
                    i = i + 1;
                    j = 0;
                    if (i>=N)
                        return true;
                }


        for (int num = 1; num<=N; num++) {
            if (CheckIfSafe(i, j, num)) {
                mat[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                mat[i][j] = 0;
            }
        }
        return false;
    }

    /**
     * Feltölti az üres helyeket a 6x6 és 4x4 es táblákhoz
     * @return
     */
    private boolean fillRemain(){
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++) {
                while (mat[i][j] == 0) {
                    for (int num = 1; num <= N; num++)
                    if (CheckIfSafe(i, j, num)){
                        mat[i][j] = num;
                        break;
                    }
                    if (mat[i][j] == 0) return false;
                }
            }
        }
        return true;
    }

    /**
     * K számot kitöröl a táblábol
     * @param K a törlendő számok száma
     */
    public void removeKDigits(int K)
    {
        int count = K;
        while (count != 0) {
            int cellId = (int) Math.floor((Math.random()*(N*N)));
            int i = (cellId/N);
            int j = cellId%N;

            if (mat[i][j] != 0)
            {
                count--;
                mat[i][j] = 0;
            }
        }
    }

    /**
     * Visszaadja a táblát
     * @return
     */
    public int[][] getMat() {
        return mat;
    }
}
