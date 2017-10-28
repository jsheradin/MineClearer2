import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class gameMain extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("MineClearer2");

        Group menuGroup = new Group();
        Scene menuScene = new Scene(menuGroup, 250, 150, Color.WHITE);

        TextField wideBox = new TextField ();
        TextField tallBox = new TextField();
        TextField bombBox = new TextField();
        TextField sizeBox = new TextField();

        Button restart = new Button("New game");
        restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setScene(menuScene);
                primaryStage.show();
            }
        });

        Button start = new Button("New game");
        start.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                //Validate input
                int inputWide = Integer.parseInt(wideBox.getCharacters().toString());
                int inputTall = Integer.parseInt(tallBox.getCharacters().toString());
                int inputBombs = Integer.parseInt(bombBox.getCharacters().toString());
                int inputSize = Integer.parseInt(sizeBox.getCharacters().toString());

                if(inputWide>0 && inputTall>0 && inputBombs>0 && inputSize>0) {
                    if (inputBombs < (inputTall * inputWide)) {
                        //Make game
                        gameSettings.newGame(inputWide*inputSize, inputTall*inputSize, inputTall, inputWide, inputBombs);

                        //Draw game board
                        Group gameBoard = new Group();
                        Scene gameScene = new Scene(gameBoard, gameSettings.getPixWide(), gameSettings.getPixTall(), Color.BLACK);

                        gameSettings.setGroup(gameBoard);
                        gameSettings.setRestart(restart);

                        for (int i = 0; i < gameSettings.board.length; i++) {
                            gameBoard.getChildren().add(gameSettings.board[i].getRect(gameBoard));
                            gameBoard.getChildren().add(gameSettings.board[i].getBomb());
                        }

                        for (int x = 0; x < gameSettings.getBlocksWide(); x++) {
                            Line div = new Line(x * gameSettings.getBlockPixWide(), 0, x * gameSettings.getBlockPixWide(), gameSettings.getPixTall());
                            div.setFill(gameSettings.getColor(6));
                            gameBoard.getChildren().add(div);
                        }

                        for (int y = 0; y < gameSettings.getBlocksTall(); y++) {
                            Line div = new Line(0, y * gameSettings.getBlockPixTall(), gameSettings.getPixWide(), y * gameSettings.getBlockPixTall());
                            div.setFill(gameSettings.getColor(6));
                            gameBoard.getChildren().add(div);
                        }

                        primaryStage.setScene(gameScene);
                        primaryStage.show();
                    }
                }
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

        grid.add(new Label("Blocks Wide: "), 0, 0);
        grid.add(new Label("Blocks Tall: "), 0, 1);
        grid.add(new Label("Bombs: "), 0, 2);
        grid.add(new Label("Block Size: "), 0, 3);
        grid.add(wideBox, 1, 0);
        grid.add(tallBox, 1, 1);
        grid.add(bombBox, 1, 2);
        grid.add(sizeBox, 1, 3);
        grid.add(start, 0, 4);

        menuGroup.getChildren().add(grid);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

}
