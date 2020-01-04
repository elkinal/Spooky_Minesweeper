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
    *   Features that should be added:
    * >>The numbers on the tiles should be hidden until they are pressed
    * >>A Game Over screen should be shown to the use when the game is lost
    * >>The Buttons should be styled. There should be images representing the numbers, and the empty tiles
    * >>As an extension to this, different themes could be added so that the user can customize the game board
    * */

    private static int[][] mines = new int[10][10];

    private GridPane root = new GridPane(); //Initializing the pane here so it can be accessed in any method

    @Override
    public void start(Stage s) throws Exception {

        //Adding a title to the game
        s.setTitle("Spooky Minesweeper");


        //Styling the scene
        styleBoard();
        initBoard();
//
//        //Initialing the GridPane
//        root = new GridPane();

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

    private void buttonPressed(String id) {

        //Storing the coordinates of the button
        String[] coords = id.split(",");
        Point2D point = new Point2D(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));

        //Change the text on the button that was pressed
        ((Button) root.lookup("#" + id)).setText(String.valueOf(mines[Integer.parseInt(coords[0])][Integer.parseInt(coords[1])]));

        //check if bomb or normal tile, then adapt array
        if(mines[Integer.parseInt(coords[0])][Integer.parseInt(coords[1])] == -1)
            gameOver();
    }

    private void initBoard() {
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


        //Filling the GridPane
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[0].length; j++) {

                //The argument is the text that is displayed on the buttons
                Button intermediate = new Button((mines[i][j] == -1) ? "X" : "");

                //Sets the preferred size of the tiles
                intermediate.setPrefSize(50, 50);

                //Sets the ID of the buttons equal to their coordinates (separated by a comma)
                intermediate.setId(i + "," + j);

                // TODO: 03/01/2020 This is a stilt/workaround solution. A new listener should not be implemented for each button; this is inefficient
                intermediate.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        buttonPressed(intermediate.getId()); //Passes the COORDINATES of the button to buttonPressed()
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

    private void styleBoard() {
        root.setHgap(8);
        root.setVgap(8);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
