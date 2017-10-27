/*  Each square on the minefield is a block object
 *  Each block has a JavaFX Rectangle that is shown on the board
 */

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class block {
    boolean isEdge = false;

    boolean isBomb = false;
    int bombsAround = 0;

    boolean flagged = false;
    boolean cleared = false;

    int position;

    Rectangle rect = new Rectangle();
    Text num = new Text();
    Ellipse bomb = new Ellipse();

    block[] blocksAround = new block[8];

    public void setPosition(int pos) {
        position = pos;
    }

    public boolean isEdge() {
        return isEdge;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
        rect.setFill(this.getColor());
        if(!isBomb && bombsAround != 0) {
            num.setVisible(true);
        }
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public void setEdge(boolean edge) {
        isEdge = edge;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public int getBombsAround() {
        return bombsAround;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public block[] getBlocksAround() {
        return blocksAround;
    }

    public void setBlocksAround(int pos, block link) {
        this.blocksAround[pos] = link;
    }

    public void countBombsAround() {
        for (int i=0; i<blocksAround.length; i++) {
            if(blocksAround[i].isBomb()){
                bombsAround++;
            }
        }
    }

    public int getPalette() {
        if (!isBomb && !cleared && !flagged){
            return 0;
        } else if (isBomb && !cleared && !flagged){
            return 1;
        } else if (!isBomb && flagged){
            return 2;
        } else if (isBomb && flagged){
            return 3;
        } else if (!isBomb && cleared){
            return 4;
        } else if (isBomb && cleared){
            return 5;
        } else { //??
            return 5;
        }
    }

    public Color getColor(){
        return gameSettings.getColor(this.getPalette());
    }

    public void clickBlock(javafx.scene.input.MouseEvent event) {
        if(gameSettings.isGameOver()){
            return; //No action once game over
        }

        if(event.getButton() == MouseButton.PRIMARY && !flagged) {
            cleared = true;
            flagged = false;
            if (cleared && isBomb) {
                gameSettings.endGame();
            } else if (this.getBombsAround() == 0) {
                ArrayList<block> clear = new ArrayList<block>();
                clear.add(this);
                autoClear(clear);
            }
        } else if (event.getButton() == MouseButton.SECONDARY){
            if(!cleared) {
                flagged = !flagged;
            }
        }
        rect.setFill(this.getColor());
        if(!isBomb && bombsAround != 0) {
            num.setVisible(true);
        }

        gameSettings.checkGameOver();
    }

    public void autoClear(ArrayList<block> clear) {
        while (clear.size() > 0) {
            block tile = clear.get(0);
            if(!tile.isCleared()) {
                tile.setCleared(true);
                tile.setFlagged(false);
            }
            for (int j = 0; j < blocksAround.length; j++) {
                block sub = tile.getBlocksAround()[j];
                if (!sub.isCleared()) {
                    sub.setCleared(true);
                    sub.setFlagged(false);
                    if (sub.getBombsAround() == 0) {
                        if (!sub.isEdge()) {
                            clear.add(sub);
                        }
                    }
                }
            }
            clear.remove(0);
        }
    }

    public Rectangle getRect(Group group){ //son
        rect.setX(gameSettings.getBlockPixWide()*(position%gameSettings.getBlocksWide()));
        rect.setY(position/gameSettings.getBlocksWide()*gameSettings.getBlockPixTall());
        rect.setWidth(gameSettings.getBlockPixWide());
        rect.setHeight(gameSettings.getBlockPixTall());
        rect.setFill(this.getColor());
        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!gameSettings.isSpawned()){
                    gameSettings.populate(position, group);
                }
                block.this.clickBlock(event);
            }
        });
        return rect;
    }

    public Text getNumber(){
        num.setText(String.valueOf(bombsAround));
        num.setX(gameSettings.getBlockPixWide()*(position%gameSettings.getBlocksWide())+gameSettings.getBlockPixWide()/2);
        num.setY(position/gameSettings.getBlocksWide()*gameSettings.getBlockPixTall()+gameSettings.getBlockPixTall());
        num.setTextAlignment(TextAlignment.LEFT);
        num.setFont(gameSettings.getFont());
        num.setFill(gameSettings.getColor(6));
        num.setVisible(false);
        num.setMouseTransparent(true);
        return num;
    }

    public Ellipse getBomb(){
        bomb.setCenterX(gameSettings.getBlockPixWide()*(position%gameSettings.getBlocksWide())+gameSettings.getBlockPixWide()/2);
        bomb.setCenterY(position/gameSettings.getBlocksWide()*gameSettings.getBlockPixTall()+gameSettings.getBlockPixTall()/2);
        bomb.setRadiusX(gameSettings.getBlockPixWide()/3);
        bomb.setRadiusY(gameSettings.getBlockPixTall()/3);
        bomb.setFill(gameSettings.getColor(6));
        bomb.setVisible(false);
        bomb.setMouseTransparent(true);
        return bomb;
    }

    public void showBomb(){
        if(isBomb){
            bomb.setVisible(true);
        }
    }

}
