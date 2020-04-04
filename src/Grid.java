import java.util.HashSet;
import java.util.Set;

public final class Grid {

    private Cell [][] grid;
    private final int sizeX;
    private final int sizeY;
    
    private Set<Entity> gridEntities = new HashSet<>();
    
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
    
    public void update() {
        for(Entity e : gridEntities) {
            e.update();
        }
        
        /*
        for(int x = 0; x<sizeX; ++x) {
            for(int y = 0; y < sizeY; ++y) {
                grid[x][y].update();
            }
        }
        */
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
    
    public void addEntity(Entity e) {
        gridEntities.add(e);
    }
    
    public void removeEntity(Entity e) {
        gridEntities.remove(e);
    }
    
    public boolean isInBorder(int x, int y) {
        return (0 <= x && x < sizeX && 0 <= y && y < sizeY);
    }
}
