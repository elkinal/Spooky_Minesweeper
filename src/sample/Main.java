package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {



    /*
    * Consider making the tiles out of buttons. This would mean that the frame rate will not need to be manually initialized
    * Instead, standard GUI tools integrated into JavaFX can be used. This will make the whole application much cleaner and more enjoyable to use
    * A menu should also be added, just like the one you saw in the website 02/01/2020.
    *
    * The main issue with the development of this game is the fact that I do not know how to initialize the board by drawing the buttons
    * For this, knowledge of JavaFX will be required*/

    private static int[][] mines = new int[10][10];

    @Override
    public void start(Stage s) throws Exception {

        //Adding a title to the game
        s.setTitle("Spooky Minesweeper");

        GridPane root = new GridPane();

        //Styling the scene
        styleBoard(root);
        initBoard(root);

        //Creating a scene
        Scene sc = new Scene(root, 500, 500);

        //Setting the scene
        s.setScene(sc);
        s.show();



    }



    private boolean borderCell(int i, int j) {
        return i == 0 || i == mines.length - 1 || j == 0 || j == mines[0].length - 1;
    }


    private static int rand(int min, int max) {

        if (min >= max)
            throw new IllegalArgumentException("max must be greater than min");

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void buttonPressed(String[] coords) {
        //This is where I find out the coordinates
        Point2D point = new Point2D(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));


        //check if bomb or normal tile, then adapt array
        if(mines[Integer.parseInt(coords[0])][Integer.parseInt(coords[1])] == -1)
            gameOver();
    }

    private void initBoard(GridPane root) {
        //Planting the mines
        for (int i = 0; i < mines.length; i++)
            for (int j = 0; j < mines[0].length; j++)
                if(rand(0, 8) == 0)
                    mines[i][j] = -1;

        //Setting the numbers on the board
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[0].length; j++) {

                int mineNumber = 0;

                //Detecting the number of mines in adjacent cells for every cell
                if(!borderCell(i, j) && mines[i][j] != -1) { //Ensuring that an ArrayIndexOutOfBoundsException is not thrown
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            if(mines[i+k][j+l] == -1)
                                mineNumber++;
                        }
                    }
                    mines[i][j] = mineNumber; //If the current one isn't -1
                }
            }
        }

        //Filling the gridpane
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[0].length; j++) {
                Button intermediate = new Button((mines[i][j] == -1) ? "X" : String.valueOf(mines[i][j]));
                intermediate.setPrefSize(50, 50);

                intermediate.setId(i + "," + j);

                // TODO: 03/01/2020 This is a stilt/workaround solution. A new listener should not be implemented for each button; this is inefficient
                intermediate.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        buttonPressed(intermediate.getId().split(",")); //get the id of this mouse that is pressed
                    }
                });

                GridPane.setConstraints(intermediate, i, j, 1, 1);
                root.getChildren().add(intermediate);
            }
        }
    }

    private void gameOver() {
        // TODO: 03/01/2020 Change the screen to the game over screen
        System.err.println("Game Over");
    }

    private void styleBoard(GridPane root) {
        root.setHgap(8);
        root.setVgap(8);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
