package Entities;
import GameEnvironment.Grid;

public abstract class Buildings extends Entity{

    private int capacity;
    private int occupants;
    
    public Buildings(int x, int y, Grid area, int capacity){
        super(x, y, area);
        this.capacity = capacity; 
        occupants=0;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public boolean isFull() {
        return occupants>=capacity;
    }
}
