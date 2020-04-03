
public abstract class Entity {

    private  int posX;
    private int posY;
    private Grid aera;
    private boolean takeCellSpace; 
    private Cell currCell;
    
    private String printChar;
    
    public Entity(int x, int y, Grid aera, boolean takeCellSpace, String printChar) {
        setPosX(x);
        setPosY(y);
        this.aera = aera;
        this.takeCellSpace = takeCellSpace;
        setCurrCell(aera.getCell(x, y));
        this.printChar = printChar;
    }

    public boolean takeCellSpace() {
        return takeCellSpace;
    }
    
    
    public abstract void update();

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Grid getGrid() {
        return aera;
    }

    public Cell getCurrCell() {
        return currCell;
    }

    public void setCurrCell(Cell currCell) {
        this.currCell = currCell;
    }
    
    public String toString() {
        return printChar;
    }

}
