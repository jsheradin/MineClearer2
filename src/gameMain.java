/*  Minesweeper clone for fun
 *  By Jacob Sheradin
 *  Java 8 with Processing 3.3.6
*/

import processing.core.PApplet;

public class gameMain extends PApplet{
    static block[] board;

    public static void main(String[] args){
        //TODO Launch menu
        board = gameSettings.newGame(800, 800, 10, 10, 10);
        PApplet.main("gameMain", args);
    }

    public void settings() {
        size(gameSettings.getPixWide(), gameSettings.getPixTall());
    }

    public void setup() {
        background(255);
    }

    public void mouseClicked(){
        //Action on tile from click
        int x = floor(mouseX/gameSettings.getBlockPixWide());
        int y = floor(mouseY/gameSettings.getBlockPixTall());
        if(!gameSettings.isGameOver(board)) {
            if (mouseButton == LEFT) {
                board[y * gameSettings.getBlocksWide() + x].clickBlock();
            } else if (mouseButton == RIGHT) {
                board[y * gameSettings.getBlocksWide() + x].flagBlock();
            }
        }
    }

    public void draw() {
        //Place tiles on the board
        for(int x=0; x<gameSettings.getBlocksWide(); x++){
            for(int y=0; y<gameSettings.getBlocksTall(); y++){
                block tile = board[y*gameSettings.getBlocksWide()+x];
                fill(tile.getColor().getRGB());
                rect(x*gameSettings.getBlockPixWide(), y*gameSettings.getBlockPixTall(), gameSettings.getBlockPixWide(), gameSettings.getBlockPixTall());
                if(tile.isCleared() && tile.getBombsAround()!=0){
                    fill(gameSettings.getColor(6).getRGB());
                    textAlign(CENTER, CENTER);
                    textSize(gameSettings.getBlockPixTall()-5);
                    text(tile.getBombsAround(), x*gameSettings.getBlockPixWide()+gameSettings.getBlockPixWide()/2, y*gameSettings.getBlockPixTall()+gameSettings.getBlockPixTall()/2);
                }
                if(gameSettings.isGameOver(board) && tile.isBomb()){
                    fill(gameSettings.getColor(6).getRGB());
                    ellipse(x*gameSettings.getBlockPixWide()+gameSettings.getBlockPixWide()/2, y*gameSettings.getBlockPixTall()+gameSettings.getBlockPixTall()/2, gameSettings.getBlockPixWide()/2, gameSettings.getBlockPixTall()/2);
                }
            }
        }
    }
}
