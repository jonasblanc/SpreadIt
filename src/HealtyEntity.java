
public class HealtyEntity extends MovableEntity {

    
    public HealtyEntity(int x, int y, Grid aera, boolean takeCellSpace) {
        super(x, y, aera, takeCellSpace, "+");
    }

    @Override
    public void update() { // Est qu'on garde Dir si on ne l'utilise pas ? (ça complique pour rien pour l'instant)
        int x = super.getPosX();
        int y = super.getPosY();
        Grid grid = super.getGrid();
        
        Dir dir = super.getDirection();
        
        
        switch(dir) {
            case LEFT:
                if  (x > 0) {
                    super.moveTo(x-1, y);
                 }else {
                     super.setDirection(Dir.RIGHT);
                 }
                break;
            case RIGHT:
                if  (x < grid.getBorderX()-1) {
                    super.moveTo(x+1, y);
                 }else {
                     super.setDirection(Dir.LEFT);
                 }
                break;
        default:
            break;
        }
    }
    
}
