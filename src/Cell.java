import java.util.ArrayList;
import java.util.List;

public final class Cell {
    private final int x;
    private final int y;
    
    private List<MovableEntity> movableEntities = new ArrayList<>();
    
    public Cell(int posX, int posY) {
        x = posX;
        y = posY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void addEntity(MovableEntity e) {
        movableEntities.add(e);
    }

}
