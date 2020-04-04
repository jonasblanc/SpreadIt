package Entities;
import GameEnvironment.Grid;

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
    public void update() {
        // TODO Auto-generated method stub
    }

}
