import java.util.HashSet;
import java.util.Set;

public final class Cell {
    private final int x;
    private final int y;
    private final Grid ownerGrid;
    
    private Set<Entity> entities = new HashSet<>();
    
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
        
        for(Entity e: entities) {
            if(e.takeCellSpace()) {
                return false;
            }
        }
        return entities.isEmpty() ? true : !entity.takeCellSpace() ;
    }
    
    public boolean addEntity(Entity e) {
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
    
   public void update() {
       for(Entity e: entities) {
           e.update();
       }
   }
   
   public boolean canLeave(Entity e) {
       return true;
   }
   
   @Override
   public String toString() {
       
       if (entities.isEmpty()) {
           return " ";
       }
       else {
           StringBuilder sb = new StringBuilder();
           for(Entity e:entities){
               sb.append(e.toString());
           }
           return sb.toString();
       }
   }
}
