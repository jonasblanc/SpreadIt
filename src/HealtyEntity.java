import java.util.ArrayList;
import java.util.List;

public class HealtyEntity extends MovableEntity {

    private final int x;
    private final int y;
    
    private List<MovableEntity> movableEntities = new ArrayList<>();
    
    public HealtyEntity(int posX, int posY) {
        x = posX;
        y = posY;
    }

}
