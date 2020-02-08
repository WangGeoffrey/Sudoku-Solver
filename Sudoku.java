import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Sudoku extends Application {

    Stage window;
    BorderPane layout;
    Scene scene;
    Board board;

    Solver solver = new Solver();
    boolean showingSolve = false;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setResizable(false);
        window.setTitle("Sudoku");

        board = new Board (solver.getBoard());

        Button resetButton = new Button("Reset");
        Button solveButton = new Button("Solve");
        Button changeButton = new Button("New Board");

        resetButton.setOnAction(e -> {
            board.reset(solver.getBoard());
        });

        solveButton.setOnAction(e -> {
            board.reset(solver.getBoard());
            solver.updateBoard(board);
            if (!(board.getPrevious()[0] < 0 || board.getPrevious()[1] < 0)) {
                board.getBoxAt(board.getPrevious()[0], board.getPrevious()[1]).deselect();
            }
            if (solver.getSelected() == 1 || solver.getSelected() == 2) {
                showingSolve = true;
                solver.showSolve().play();
            }
            board.setSolved(true);
        });

        changeButton.setOnAction(e -> {
            if (showingSolve) {
                solver.showSolve().stop();
            }
            solver.changeBoard();
            board = new Board (solver.getBoard());
            layout.setCenter(board.getDisplayBoard());
            scene = new Scene(layout, 450, 500);
            window.setScene(scene);
            window.show();
        });

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setSpacing(20);
        buttons.getChildren().addAll(resetButton, solveButton, changeButton);

        layout = new BorderPane();
        layout.setCenter(board.getDisplayBoard());
        layout.setBottom(buttons);
        
        scene = new Scene(layout, 450, 500);
        window.setScene(scene);
        window.show();
    }

} //End class