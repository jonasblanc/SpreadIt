package Entities;
import GameEnvironment.Grid;

public final class Hospital extends Buildings {

    public Hospital(int x, int y, Grid area, int capacity){
        super(x, y, area, capacity);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public String toString() {
        return "+";
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

}
