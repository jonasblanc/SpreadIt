
public final class Grid {

    private Cell [][] grid;
    private final int sizeX;
    private final int sizeY;
    
    public Grid(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        initGrid();
    }

    private final void initGrid() {
        grid = new Cell[sizeX][sizeY];
        for(int x = 0; x<sizeX; ++x) {
            for(int y = 0; y < sizeY; ++y) {
                grid[x][y] = new Cell(x,y);
            }
        }
    }
    
    public Cell getCell(int x, int y) {
        return grid[x][y];
    }
    
    public void update() {
        for(int x = 0; x<sizeX; ++x) {
            for(int y = 0; y < sizeY; ++y) {
                grid[x][y].update();
            }
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int y = sizeY-1; y >= 0; --y) {
            for(int x = 0; x<sizeX; ++x) {
                sb.append(grid[x][y].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
        
    }
}
