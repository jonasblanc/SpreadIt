public class Wall extends Entity{

    /**
     * 
     * @param x
     * @param y
     * @param area
     */
    public Wall(int x, int y, Grid area) {
        super(x, y, area);
    }


    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public String toString() {
        return "*";
    }


    @Override
    public void update(int time) {
    }
    
     public void draw(PGraphics gameSurface){
        gameSurface.noStroke();
      gameSurface.fill(C_WALL);
      gameSurface.rect(0,0,CELL_SIZE,CELL_SIZE);
    }

}
