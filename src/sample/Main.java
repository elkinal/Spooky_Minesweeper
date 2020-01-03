package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
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

        root.setHgap(8);
        root.setVgap(8);


        //Filling the mines array
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[0].length; j++) {
                mines[i][j] = rand(0, 9);
            }
        }

        //Filling the gridpane
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[0].length; j++) {
                Button intermediate = new Button(String.valueOf(mines[i][j]));
                intermediate.setPrefSize(50, 50);
                GridPane.setConstraints(intermediate, i, j, 1, 1);
                root.getChildren().add(intermediate);
            }
        }

        /*
        Figure out which button is pressed
         */



        //Creating a scene
        Scene sc = new Scene(root, 500, 500);

        //Setting the scene
        s.setScene(sc);
        s.show();



    }


    private static int rand(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
