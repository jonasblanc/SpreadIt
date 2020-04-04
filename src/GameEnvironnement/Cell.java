package GameEnvironnement;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import Entities.Entity;

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
    
    
    /**
     * Return a set of cells in radius r
     * @param r - radius
     * @return
     */
    private Set<Cell> getRadiusCells(int r){
        Set<Cell> cells = new HashSet<>();
        for(int tryX = x-r; tryX<=x+r; ++tryX) {
            for(int tryY = y-r; tryY<=y+r; ++tryY) {
                if(Math.abs(tryX - x) + Math.abs(tryY - y) <= r) {
                    if(ownerGrid.isInBorder(tryX, tryY)) {
                        cells.add(ownerGrid.getCell(tryX, tryY));
                    }
                   
                }
            } 
        }
        return cells;
    }
    
    /**
     * @return a copy of the set of entities
     */
    public Set<Entity> getEntities(){
        return new HashSet<>(entities);
    }
    
    /**
     * Return the entities in a radius r
     * @param r - radius
     * @return 
     */
    public Set<Entity> getRadiusEntities(int r){
        Set<Cell> cells = getRadiusCells(r);
        Set<Entity> es = cells.stream().flatMap(c -> c.getEntities().stream()).collect(Collectors.toSet());
        return es;
    }
    
    /**
     * Return if the entity can enter the cell
     * @param entity
     * @return
     */
    public boolean canEnter(Entity entity) {
        for(Entity e: entities) {
            if(e.takeCellSpace()) {
                return false;
            }
        }
        return entities.isEmpty() ? true : !entity.takeCellSpace() ;
    }
    
    /**
     * Return if the entity can leave the cell
     * @param e
     * @return
     */
    public boolean canLeave(Entity e) {
        return true;
    }
    
    /**
     * Add the entity in the cell if possible
     * @param e
     * @return if adding was a success
     */
    public boolean addEntity(Entity e) {
        if(canEnter(e)){
            entities.add(e);
            return true;
        }  
        return false;
    }
    
    /**
     * Remove the entity of the cell if possible
     * @param e
     * @return if removing was a success
     */
    public boolean removeEntity(Entity e) {
        if(canLeave(e)) {
            entities.remove(e);
            return true;
        }
        return false;
    }
   
   public int getX() {
       return x;
   }

   public int getY() {
       return y;
   }
   
   @Override
   public String toString() {
       
       if (entities.isEmpty()) {
           return " ";
       }
       else {
           StringBuilder sb = new StringBuilder();
           if(entities.size() >1) {
               sb.append("(");
           }
           for(Entity e:entities){
               sb.append(e.toString());
           }
           if(entities.size() >1) {
               sb.append(")");
           }
           return sb.toString();
       }
   }
}
