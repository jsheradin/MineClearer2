import java.awt.*;

public class gameSettings {
    public static int pixWide;
    public static int pixTall;
    public static int blocksWide;
    public static int blocksTall;
    public static int bombs;

    public static boolean gameOver;

    public static Color[] palette = new Color[7];

    public static block[] newGame(int newpixWide, int newpixTall, int newblocksTall, int newblocksWide, int newbombs) {
        //Create a new game
        pixWide = newpixWide;
        pixTall = newpixTall;
        blocksTall = newblocksTall;
        blocksWide = newblocksWide;
        bombs = newbombs;

        gameOver = false;

        //Default colors
        for(int i=0; i<palette.length; i++){
            //TODO remove test values
            palette[0] = Color.GRAY; //Safe, unclicked
            palette[1] = Color.GRAY; //Bomb, unclicked
            palette[2] = Color.PINK; //Safe, flagged
            palette[3] = Color.PINK; //Bomb, flagged
            palette[4] = Color.GREEN; //Safe, cleared
            palette[5] = Color.RED; //Detonated
            palette[6] = Color.BLACK; //Text
        }

        //Make the grid
        block edge = new block();
        edge.setEdge(true);
        block[] grid = new block[blocksTall*blocksWide];
        for(int i=0; i<grid.length; i++){
            grid[i]=new block();
        }
        for(int x=0; x<blocksWide; x++){
            for(int y=0; y<blocksTall; y++){
                int i = (y*blocksWide)+x;
                //System.out.printf("i: %d, (%d,%d)\n", i, x, y);
                //Top left
                if(x>0 && y>0){
                    grid[i].setBlocksAround(0, grid[i-blocksWide-1]);
                } else {
                    grid[i].setBlocksAround(0, edge);
                }
                //Top center
                if(y>0){
                    grid[i].setBlocksAround(1, grid[i-blocksWide]);
                } else {
                    grid[i].setBlocksAround(1, edge);
                }
                //Top right
                if(x<blocksWide-1 && y>0){
                    grid[i].setBlocksAround(2, grid[i-blocksWide+1]);
                } else {
                    grid[i].setBlocksAround(2, edge);
                }
                //Left
                if(x>0){
                    grid[i].setBlocksAround(3, grid[i-1]);
                } else {
                    grid[i].setBlocksAround(3, edge);
                }
                //Right
                if(x<blocksWide-1){
                    grid[i].setBlocksAround(4, grid[i+1]);
                } else {
                    grid[i].setBlocksAround(4, edge);
                }
                //Bottom left
                if(x>0 && y<blocksTall-1){
                    grid[i].setBlocksAround(5, grid[i+blocksWide-1]);
                } else {
                    grid[i].setBlocksAround(5, edge);
                }
                //Bottom center
                if(y<blocksTall-1){
                    grid[i].setBlocksAround(6, grid[i+blocksWide]);
                } else {
                    grid[i].setBlocksAround(6, edge);
                }
                //Bottom right
                if(x<blocksWide-1 && y<blocksTall-1){
                    grid[i].setBlocksAround(7, grid[i+blocksWide+1]);
                } else {
                    grid[i].setBlocksAround(7, edge);
                }
            }
        }

        if(bombs>blocksTall*blocksWide){
            System.out.printf("Too many bombs (%d)", bombs);
            System.exit(1);
        }

        //Fair(tm) bomb placing algorithm
        int bombsPlaced = 0;
        while (bombsPlaced < bombs){
            int i = (int) (Math.random()*grid.length);
            if (!grid[i].isBomb()){
                grid[i].setBomb(true);
                bombsPlaced++;
            }
        }
        for(int i=0; i<grid.length; i++){
            grid[i].countBombsAround();
        }

        return grid;
    }

    public static int getBlocksTall() {
        return blocksTall;
    }

    public static int getBlocksWide() {
        return blocksWide;
    }

    public static int getPixTall() {
        return pixTall;
    }

    public static int getPixWide() {
        return pixWide;
    }

    public static int getBombs() {
        return bombs;
    }

    public static int getBlockPixWide() {
        return pixWide/blocksWide;
    }

    public static int getBlockPixTall() {
        return pixTall/blocksTall;
    }

    public static void setPalette(Color safeUnclicked, Color bombUnclicked, Color safeFalgged, Color bombFlagged, Color safeCleared, Color detonated, Color text){
        palette[0] = safeUnclicked;
        palette[1] = bombUnclicked;
        palette[2] = safeFalgged;
        palette[3] = bombFlagged;
        palette[4] = safeCleared;
        palette[5] = detonated;
        palette[6] = text;
    }

    public static Color getColor(int state){
        return palette[state];
    }

    public static boolean isGameOver(block[] board){
        int checked = 0;
        for(int i=0; i<board.length; i++){
            if (board[i].isCleared()){
                checked++;
            }
        }

        if(checked >= (blocksWide*blocksTall)-bombs){
            return true;
        } else {
            return gameOver;
        }
    }

    public static void endGame(){
        gameOver = true;
    }

}
