public final class House extends Buildings {

    public House(int x, int y, Grid area, int capacity){
        super(x, y, area, capacity);
    }

    @Override
    public boolean takeCellSpace() {
        return isFull();
    }

    @Override
    public String toString() {
        return "^";
    }

    @Override
    public void update(int time) {
        // TODO Auto-generated method stub
    }

 public void draw(PGraphics gameSurface){
    gameSurface.noStroke();
      gameSurface.fill(C_HOUSE);
      gameSurface.rect(0,0,CELL_SIZE,CELL_SIZE);
    }
}
