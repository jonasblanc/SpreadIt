import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class Cell {
    private final int x;
    private final int y;
    private final Grid ownerGrid;
    
    private Set<Entity> entities = new HashSet();
    
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
        Set<Cell> cells = new HashSet();
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
        return new HashSet(entities);
    }
    
    public Set<Human> getHumans(){
        Set<Human> humans = new HashSet();
        
        for(Entity e: entities) {
            if(e instanceof Human) {
                humans.add((Human) e);
            }
        }
        return humans;
    }
    
    /**
     * Return the entities in a radius r
     * @param r - radius
     * @return 
     */
    public Set<Entity> getRadiusEntities(int r){
        Set<Cell> cells = getRadiusCells(r);
        Set<Entity> es = new HashSet();
        for(Cell c :cells){
          for(Entity e: c.getEntities()){
            es.add(e);
          }
        }
        return es;
    }
    
    public Set<Infectable> getRadiusInfectables (int r){
        
        Set<Entity> es =getRadiusEntities(r);
        Set<Infectable> infectables = new HashSet();
        
        for(Entity e: es) {
            if(e instanceof Infectable) {
                infectables.add((Infectable) e);
            }
        }
        return infectables;
    }
    
    public boolean hasHospital() {
        for(Entity e: entities) {
            if(e instanceof Hospital) {
               return true;
            }
        }
       return false;
    }
    
    public boolean hasHouse() {
        for(Entity e: entities) {
            if(e instanceof House) {
               return true;
            }
        }
       return false;
    }
    
    public boolean hasWorkPlace() {
        for(Entity e: entities) {
            if(e instanceof WorkPlace) {
               return true;
            }
        }
       return false;
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
   
   public int getNumberOfEntities() {
       return entities.size();
   }
   
   public Buildings getBuilding() {
      for(Entity e: entities){
        if( e instanceof Buildings){
          return ((Buildings) e);
        }
      }
      return null;
    }
    
    public List<Entity> getAdults() {
      List<Entity> es = new ArrayList();
      for(Entity e: entities){
        if( e instanceof Adult){
          es.add(e);
        }
      }
      return es;
    }
    
    public List<Entity> getChildren() {
      List<Entity> es = new ArrayList();
      for(Entity e: entities){
        if( e instanceof Child){
          es.add(e);
        }
      }
      return es;
    }
    
    public List<Entity> getElder() {
      List<Entity> es = new ArrayList();
      for(Entity e: entities){
        if( e instanceof Elder){
          es.add(e);
        }
      }
      return es;
    }
   
   public void draw(PGraphics gameSurface){
      gameSurface.fill(color(255,255,255));
      Buildings b = getBuilding();
      if(b != null){
        b.draw(gameSurface);
      }
      List<Entity> adults = getAdults();
      List<Entity> children = getChildren();
      List<Entity> elders = getElder();
      List<Entity> sortEntities = new ArrayList();
      sortEntities.addAll(elders);
      sortEntities.addAll(adults);
      sortEntities.addAll(children);
      int numEntities = sortEntities.size();
      int humanPerRow =(int) Math.ceil(Math.sqrt(numEntities)); 
      int defaultHumanPerRow = CELL_SIZE/HUMAN_DIAMETER;
      float scale = min(1, 0.5*(float)defaultHumanPerRow / humanPerRow) ;
       
      System.out.println("Cell: " +scale);
      int countX = 0; 
      int countY = 0;
       gameSurface.pushMatrix();
       gameSurface.scale(scale);
       
       gameSurface.translate(HUMAN_DIAMETER/2, HUMAN_DIAMETER/2);
      for(Entity e: sortEntities){
        gameSurface.pushMatrix();
        gameSurface.translate(countX * HUMAN_DIAMETER, countY * HUMAN_DIAMETER);
        e.draw(gameSurface);
        
        gameSurface.popMatrix();
        countX++;
        if(countX == humanPerRow){
          countX=0;
          countY++;
        }
        
      }
      gameSurface.popMatrix();
}
}
