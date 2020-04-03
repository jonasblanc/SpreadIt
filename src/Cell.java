import java.util.ArrayList;
import java.util.List;

import com.sun.tools.doclint.Entity;

public final class Cell {
    private final int x;
    private final int y;
    private final Grid ownerGrid;
    
    private List<Entity> entities = new ArrayList<>();
    
    public Cell(int posX, int posY, Grid grid) {
        x = posX;
        y = posY;
        this.ownerGrid=grid; 
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean canEnter(Entity entity) {
        for(Entity entity: entities) {
            if(entity.takeCellSpace()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addEntity(Interactables e) {
        if(canEnter(e)){
            entities.add(e);
            return true;
        }  
        return false;
    }
    
    public boolean removeEntity(Entity e) {
        if(canLeave(e)) {
            entities.remove(e);
            return true;
        }
        return false;
    }
    
   public void applyToAll() {
       
   }
   
   public boolean canLeave(Entity e) {
       return true;
   }
   
   @Override
   public String toString() {
       entities.get(0).toString();
   }

    
}
