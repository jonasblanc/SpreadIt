package Entities;
import GameEnvironnement.Grid;

public abstract class Buildings extends Entity{

    private int capacity;
    
    public Buildings(int x, int y, Grid area, int capacity){
        super(x, y, area);
        this.capacity = capacity; 
    }
    
    public int getCapacity() {
        return capacity;
    }
}
