import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class Grid {

    private Cell [][] grid;
    private final int sizeX;
    private final int sizeY;
    
    private Set<Entity> gridEntities = new HashSet();
    
    private Set<Entity> toDelete = new HashSet();
    
    public Grid(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        initGrid();
    }

    private final void initGrid() {
        grid = new Cell[sizeX][sizeY];
        for(int x = 0; x<sizeX; ++x) {
            for(int y = 0; y < sizeY; ++y) {
                grid[x][y] = new Cell(x,y, this);
            }
        }
    }
    
    public Cell getCell(int x, int y) {
        return grid[x][y];
    }
    
    public void update(int time) {
        for(Entity e : gridEntities) {
            e.update(time);
        }
        gridEntities.removeAll(toDelete);
        toDelete.clear();
    }
    
    private int distance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1- x2) + Math.abs(y1- y2);
    }
    
    public Hospital getNearestHospital(int x, int y) {
      Set<Entity> es = new HashSet();
      for(Entity e: gridEntities){
        if( e instanceof Hospital){
          es.add(e);
        }
      }
      
        int minDIst = sizeX + sizeY + 1;
        Hospital nearestHospital = null;
        for(Entity e: es) {
            int dist = distance(x, y, e.getPosX(), e.getPosY());
            if(dist < minDIst) {
                minDIst = dist;
                nearestHospital = (Hospital) e;
            }
        }
        return nearestHospital;
    }
    
    
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int x = 0; x<sizeX; ++x) {
            sb.append("**");
        }
        sb.append("**\n*");
        for(int y = sizeY-1; y >= 0; --y) {
            for(int x = 0; x<sizeX; ++x) {
                sb.append(grid[x][y].toString()).append(" ");
            }
            sb.append("*\n*");
        }
        for(int x = 0; x<sizeX; ++x) {
            sb.append("**");
        }
        sb.append("*");
        return sb.toString();
        
    }
    
    public int getBorderX() {
        return sizeX;
    }
    
    public int getBorderY() {
        return sizeY;
    }
    
    public void addEntity(Entity e) {
        gridEntities.add(e);
    }
    
    public void removeEntity(Entity e) {
        gridEntities.remove(e);
    }
    
    public boolean isInBorder(int x, int y) {
        return (0 <= x && x < sizeX && 0 <= y && y < sizeY);
    }
    
    public void addEntityToDelete(Entity e) {
        toDelete.add(e);
    }
    
    public int howManyInfected() {
      Set<Entity> es = new HashSet();
      for(Entity e: gridEntities){
        if( e instanceof Infectable && 
                ((Infectable)e).isInfected()){
          es.add(e);
        }
      }
        return es.size();
    }
    
    public int howManyHuman() {
       Set<Entity> es = new HashSet();
      for(Entity e: gridEntities){
        if( e instanceof Human){
          es.add(e);
        }
      }
        return es.size();
    }
    
    public int howManyEntites() {
        return gridEntities.size();
    }
    
    public void draw(PGraphics gameSurface){
      gameSurface.beginDraw();
      gameSurface.background(C_BACKGROUND);
       for(int y =0; y < sizeY; ++y) {
            for(int x = 0; x<sizeX; ++x) {
                gameSurface.pushMatrix();
                gameSurface.translate(x*CELL_SIZE,(y)*CELL_SIZE);
                grid[x][y].draw(gameSurface);
                gameSurface.popMatrix();
            }
        }
      gameSurface.endDraw();
    }
}
