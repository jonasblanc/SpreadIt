package Entities;

import GameEnvironment.Grid;

public class WorkPlace extends Buildings{

    /**
     * @param x : x position
     * @param y : y position
     * @param area : grid the workPlace is in
     * @param capacity
     */
    public WorkPlace(int x, int y, Grid area, int capacity) {
        super(x, y, area, capacity);
    }

    @Override
    public boolean takeCellSpace() {
        return isFull();
    }

    @Override
    public String toString() {
        return "W";
    }


    @Override
    public void update(int time) { 
    }

}
