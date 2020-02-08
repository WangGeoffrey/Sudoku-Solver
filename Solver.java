import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Solver {

    private Timeline solve = new Timeline ();
    private double delay = 0;
    private double speed = 0.1;
    private int selected = 1;
    private boolean visualize = true;

    private int[][] board1 = {
        {7,8,0,4,0,0,1,2,0},
        {6,0,0,0,7,5,0,0,9},
        {0,0,0,6,0,1,0,7,8},
        {0,0,7,0,4,0,2,6,0},
        {0,0,1,0,5,0,9,3,0},
        {9,0,4,0,6,0,0,0,5},
        {0,7,0,3,0,0,0,1,2},
        {1,2,0,0,0,7,4,0,0},
        {0,4,9,2,0,6,0,0,7}
    };

    private int[][] board2 = {
        {5,3,0,0,7,0,0,0,0},
        {6,0,0,1,9,5,0,0,0},
        {0,9,8,0,0,0,0,6,0},
        {8,0,0,0,6,0,0,0,3},
        {4,0,0,8,0,3,0,0,1},
        {7,0,0,0,2,0,0,0,6},
        {0,6,0,0,0,0,2,8,0},
        {0,0,0,4,1,9,0,0,5},
        {0,0,0,0,8,0,0,7,9}
    };

    private int[][] board3 = {
        {0,0,0,8,0,1,0,0,0},
        {0,0,0,0,0,0,4,3,0},
        {5,0,0,0,0,0,0,0,0},
        {0,0,0,0,7,0,8,0,0},
        {0,0,0,0,0,0,1,0,0},
        {0,2,0,0,3,0,0,0,0},
        {6,0,0,0,0,0,0,7,5},
        {0,0,3,4,0,0,0,0,0},
        {0,0,0,2,0,0,6,0,0}
    };

    private int[][] board = new int [9][9];

    public Solver () {
        setBoard(board1);
    }

    public void setBoard (int [][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    public void changeBoard () {
        if (selected == 3) {
            setBoard(board1);
            selected = 1;
            delay = 0;
            speed = 0.1;
            visualize = true;
        }
        else if (selected == 1) {
            setBoard(board2);
            selected = 2;
            delay = 0;
            speed = 0.01;
        }
        else {
            setBoard(board3);
            selected = 3;
            visualize = false;
        }
    }
        
    public boolean updateBoard(Board board) {
        int[] emptyPoint = emptySpace();
        boolean result = false;
        if (emptyPoint == null) {
            return true;
        }
        else {
            for (int i = 1; i < 10; i++) {
                if (isValid(emptyPoint, i)) {
                    this.board[emptyPoint[0]][emptyPoint[1]] = i;
                    int setValue = i;
                    if (visualize) {
                        delay += speed;
                        solve.getKeyFrames().add( new KeyFrame( Duration.seconds(delay), 
                            e -> board.getBoxAt(emptyPoint[1], emptyPoint[0]).setText("" + setValue) ) );
                    }
                    else {
                        board.getBoxAt(emptyPoint[1], emptyPoint[0]).setText("" + setValue);
                    }
                    result = updateBoard(board);
                    if (!result) {
                        this.board[emptyPoint[0]][emptyPoint[1]] = 0;
                        if (visualize) {
                            delay += speed;
                            solve.getKeyFrames().add( new KeyFrame( Duration.seconds(delay), 
                                e -> board.getBoxAt(emptyPoint[1], emptyPoint[0]).setText("") ) );
                        }
                        else {
                            board.getBoxAt(emptyPoint[1], emptyPoint[0]).setText("");
                        }
                    }
                }
            }
            return result;
        }
    }//End updateBoard

    public int getSelected () {
        return selected;
    }

    public Timeline showSolve () {
        return solve;
    }
    
    public int[] emptySpace() {
        int[] emptyPoint = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    emptyPoint[0] = i;
                    emptyPoint[1] = j;
                    return emptyPoint;
                }
            }
        }
        return null;
    }//End emptySpace

    public boolean isValid(int[] emptyPoint, int num) {
        for (int i = 0; i < board.length; i++) {
            if (board[emptyPoint[0]][i] == num ) {
                return false;
            }
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][emptyPoint[1]] == num) {
                return false;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[(emptyPoint[0] / 3) * 3 + i][(emptyPoint[1] / 3) * 3 + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }//End isValid

    public int[][] getBoard () {
        return board;
    }

}//End class