
public abstract class Entity {

    private  int posX;
    private int posY;
    private Grid area;
    private Cell currCell;
       
    public Entity(int x, int y, Grid area) {
        setPosX(x);
        setPosY(y);
        this.area = area;
        setCurrCell(area.getCell(x, y));
    }

    public abstract boolean takeCellSpace();
    
    public abstract String toString();
    
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
        return area;
    }

    public Cell getCurrCell() {
        return currCell;
    }

    public void setCurrCell(Cell currCell) {
        this.currCell = currCell;
    }
}
