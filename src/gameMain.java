import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class gameMain extends Application{
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        //TODO start menu
        gameSettings.newGame(1000, 1000, 40, 40, 200);
        primaryStage.setTitle("MineClearer2");

        //Draw game board
        Group gameBoard = new Group();
        Scene menuScene = new Scene(gameBoard, gameSettings.getPixWide(), gameSettings.getPixTall(), Color.BLACK);

        for(int i=0; i<gameSettings.board.length; i++) {
            gameBoard.getChildren().add(gameSettings.board[i].getRect(gameBoard));
            gameBoard.getChildren().add(gameSettings.board[i].getBomb());
        }

        for(int x=0; x<gameSettings.getBlocksWide(); x++){
            Line div = new Line(x*gameSettings.getBlockPixWide(), 0, x*gameSettings.getBlockPixWide(), gameSettings.getPixTall());
            div.setFill(gameSettings.getColor(6));
            gameBoard.getChildren().add(div);
        }

        for(int y=0; y<gameSettings.getBlocksTall(); y++){
            Line div = new Line(0, y*gameSettings.getBlockPixTall(), gameSettings.getPixWide(), y*gameSettings.getBlockPixTall());
            div.setFill(gameSettings.getColor(6));
            gameBoard.getChildren().add(div);
        }

        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

}
