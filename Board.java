import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Board {

    private Box [][] board = new Box [9][9];
    private Pane displayBoard = new Pane();
    private boolean solved;

    private int [] change = {-1, -1};

    public Board (int [][] board) {
        displayBoard.setPrefSize(460, 460);
        int shiftx = 0;
        int shifty = 0;
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                shifty = (i / 3) * 5;
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    shiftx = (j / 3) * 5;
                }
                Box box = new Box(board[i][j], j, i);
                box.setTranslateX(j * 50 + shiftx);
                box.setTranslateY(i * 50 + shifty);

                displayBoard.getChildren().add(box);

                this.board[j][i] = box;
            }
            shiftx = 0;
        }
    }

    public Pane getDisplayBoard () {
        return displayBoard;
    }

    public Box getBoxAt (int x, int y) {
        return board[x][y];
    }

    public int [] getPrevious () {
        return change;
    }

    public void setSolved (boolean solved) {
        this.solved = solved;
    }

    public void reset (int [][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++){
                if (board[i][j] == 0) {
                    this.board[j][i].setText("");
                }
            }
        }
    }

    

    public class Box extends StackPane {
        private Text text = new Text();
        private Rectangle border;

        public Box(int value, int posx, int posy) {
            border = new Rectangle(50, 50);
            border.setFill(null);
            border.setStroke(Color.BLACK);
    
            text.setFont(Font.font(36));
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            if (value == 0) {
                text.setFill(Color.GREEN);
                setOnMouseClicked(event -> {
                    if (!solved) {
                        if (change[0] < 0 || change[1] < 0) {
                            select();
                            change[0] = posx;
                            change[1] = posy;
                        }
                        if (change[0] != posx || change[1] != posy) {
                            select();
                            board[change[0]][change[1]].deselect();
                            change[0] = posx;
                            change[1] = posy;
                        }
                        requestFocus();
                        setOnKeyPressed(e -> {
                            switch (e.getCode()) {
                                case DIGIT1:
                                case NUMPAD1:
                                text.setText("" + 1);
                                break;
                                case DIGIT2:
                                case NUMPAD2:
                                text.setText("" + 2);
                                break;
                                case DIGIT3:
                                case NUMPAD3:
                                text.setText("" + 3);
                                break;
                                case DIGIT4:
                                case NUMPAD4:
                                text.setText("" + 4);
                                break;
                                case DIGIT5:
                                case NUMPAD5:
                                text.setText("" + 5);
                                break;
                                case DIGIT6:
                                case NUMPAD6:
                                text.setText("" + 6);
                                break;
                                case DIGIT7:
                                case NUMPAD7:
                                text.setText("" + 7);
                                break;
                                case DIGIT8:
                                case NUMPAD8:
                                text.setText("" + 8);
                                break;
                                case DIGIT9:
                                case NUMPAD9:
                                text.setText("" + 9);
                                break;
                                case BACK_SPACE:
                                text.setText("");
                                break;
                            }
                            if (e.getCode() == KeyCode.NUMPAD1)
                                text.setText("" + 1);
                            if (e.getCode() == KeyCode.NUMPAD2)
                                text.setText("" + 2);
                            if (e.getCode() == KeyCode.NUMPAD3)
                                text.setText("" + 3);
                            if (e.getCode() == KeyCode.NUMPAD4)
                                text.setText("" + 4);
                            if (e.getCode() == KeyCode.NUMPAD5)
                                text.setText("" + 5);
                            if (e.getCode() == KeyCode.NUMPAD6)
                                text.setText("" + 6);
                            if (e.getCode() == KeyCode.NUMPAD7)
                                text.setText("" + 7);
                            if (e.getCode() == KeyCode.NUMPAD8)
                                text.setText("" + 8);
                            if (e.getCode() == KeyCode.NUMPAD9)
                                text.setText("" + 9);
                            if (e.getCode() == KeyCode.BACK_SPACE)
                                text.setText("");
                        });
                    }
                });
            }
            else {
                text.setText("" + value);
            }
    
        } //End Box constructor
    
        public void setText (String value) {
            text.setText(value);
        }
    
        public void select () {
            border.setStroke(Color.BLUE);
            border.setStrokeWidth(2);
        }
    
        public void deselect () {
            border.setStroke(Color.BLACK);
            border.setStrokeWidth(1);
        }
    
    } //End Box class

} //End Board class