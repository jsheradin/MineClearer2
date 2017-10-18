/*  Each square on the minefield is a block object
 *  The object handles actions against it and serves information to the draw loop
 */

import java.awt.*;
import java.util.ArrayList;

public class block {
    boolean isEdge = false;

    boolean isBomb = false;
    int bombsAround = 0;

    boolean flagged = false;
    boolean cleared = false;

    block[] blocksAround = new block[8];

    public boolean isEdge() {
        return isEdge;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
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

    public void clickBlock() {
        //TODO auto clear
        cleared = true;
        flagged = false;

        if(cleared && isBomb){
            gameSettings.endGame();
        } else if(this.getBombsAround() == 0){
            ArrayList<block> clear = new ArrayList<block>();
            clear.add(this);
            autoClear(clear);
        }
    }

    public void flagBlock() {
        if(!cleared) {
            flagged = !flagged;
        }
    }

    public void autoClear(ArrayList<block> clear) {
        while (clear.size() > 0) {
            System.out.printf("%d\n", clear.size());
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

}
