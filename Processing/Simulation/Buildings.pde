public abstract class Buildings extends Entity{

    private int capacity;
    
    public Buildings(int x, int y, Grid area, int capacity){
        super(x, y, area);
        this.capacity = capacity; 

    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public boolean isFull() {
        return numberOfEntities()>=capacity;
    }
    
    public int numberOfEntities() {
        return getCurrCell().getNumberOfEntities()-1;//Remove the hospital of the count
    }
}
