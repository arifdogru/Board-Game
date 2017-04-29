import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Arif on 28.04.2017.
 */
public class Game {
    /**
     * Data Fields
     */
    private Character [][] board = new Character[8][8];
    private ArrayList<Integer> path = new ArrayList<Integer>();
    private static final int SIZE = 8;
    private static final Character EMPTY = '*';
    private static final Character WAY = 'o';

    /**
     * No parameter constructor
     */
    public Game(){
        initialize();
    }

    /**
     * Initialize the main board
     */
    private void initialize(){
        char ch;
        Random randomGenerator = new Random();
        // Random number of black poll
        int numOfBlack = randomGenerator.nextInt(7)+2;
        System.out.println("num of black: "+ (numOfBlack+1));
        int row;
        int column;
        // Create Empty Table
        for (int i=0; i<SIZE; ++i){
            for (int j=0; j<SIZE; ++j){
                board[i][j] = EMPTY;
            }
        }
        board[1][0] = 'B';
        System.out.print("(" + 2 + ", A" + ") " );
        for (int i=0; i<numOfBlack;++i){
            row = randomGenerator.nextInt(8);
            column = randomGenerator.nextInt(8);
            if (row == 0 && column == 0)
                ++row;
            if (row == 7 && column == 7)
                --row;
            board[row][column] = 'B';
            ch = ((char) (column+65));
            System.out.print("(" + (row+1)+ "," + ch+ ")" + " ");
        }
        System.out.println();
    }

    /**
     * Signs the road to the board
     */
    public void printPath(){
        if(path.size() == 0) {
            System.out.println("Could not solved!");
            return;
        }
        int tempRow,tempColumn;
        for (int i=0; i< path.size();i+=2){
            tempRow = path.get(i);
            tempColumn = path.get(i+1);
            board[tempRow][tempColumn] = WAY;
        }
        board[0][0] = 'W';
        board[SIZE-1][SIZE-1] = 'E';
        System.out.println("Coordinates of the solution");
        for (int i=2; i< path.size()/2;i+=2){
            tempRow = path.get(i);
            tempColumn = path.get(i+1);
            char ch = ((char) (tempColumn+65));
            System.out.print("(" + (tempRow+1)+ "," + ch+ ")" + " ");
        }
        System.out.println();
    }

    /**
     * Displays the current board
     */
    public void displayBoard(){
        Character ch = 'A';
        System.out.print(" ");
        for (int i=1; i<=SIZE; ++i){
            System.out.print(" "+(ch++));
        }
        System.out.println();
        for (int i=0; i<SIZE; ++i){
            System.out.print((i+1)+" ");
            for (int j=0; j<SIZE; ++j){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("-----------------------");
    }

    /**
     * Find the way which arrived the finished recursively
     * This method has been prepared by using the
     * https://www.cs.bu.edu/teaching/ page
     * @param row Coordinate X
     * @param column Coordinate Y
     * @return Whether there is a way
     */
    public boolean shortestPathRecursively(int row, int column){

        // Table size control
        if(row < 0 || row >7 || column < 0 || column > 7 ){
            return false;
        }

        // Finish Point
        if(row == SIZE-1 && column == SIZE-1){
            return true;
        }

        if(board[row][column] != EMPTY){
            return false;
        }
        board[row][column] = WAY;
        path.add(row);
        path.add(column);

        // Down Control
        if(shortestPathRecursively(row+1,column) ){
            path.add(row);
            path.add(column);
            return true;
        }

        //Right Control
        if(shortestPathRecursively(row,column+1) ){
            path.add(row);
            path.add(column);
            return true;
        }

        //Left Control
        if(shortestPathRecursively(row,column-1)){
            path.add(row);
            path.add(column);
            return true;
        }

        //Up Control
        if (shortestPathRecursively(row-1,column)){
            path.add(row);
            path.add(column);
            return true;
        }
        board[row][column] = 'O';
        return false;
    }

    /**
     * Play the game
     * calls recursive method
     * calls ptintPath method
     * @return table can be solved or not
     */
    public boolean play(){
        if(!shortestPathRecursively(0,0)){
            System.out.println("Yeterli cozum bulunamadi");
            return false;
        }
        printPath();
        return true;
    }
}