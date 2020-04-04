package Entities;
import GameEnvironment.Grid;

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

}
