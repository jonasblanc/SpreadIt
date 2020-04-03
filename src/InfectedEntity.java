
public final class InfectedEntity extends MovableEntity {

    public InfectedEntity(int x, int y, Grid aera, boolean takeCellSpace) {
        super(x, y, aera, takeCellSpace, "x");
        super.setDirection(Dir.DOWN);
    }

    @Override
    public void update() {
        int x = super.getPosX();
        int y = super.getPosY();
        Grid grid = super.getGrid();
        
        Dir dir = super.getDirection();
        
        switch(dir) {
            case DOWN:
                if  (y > 0) {
                    super.moveTo(x, y-1);
                 }else {
                     super.setDirection(Dir.UP);
                 }
                break;
            case UP:
                if  (y < grid.getBorderY()-1) {
                    super.moveTo(x, y+1);
                 }else {
                     super.setDirection(Dir.DOWN);
                 }
                break;
        default:
            break;
        }
        
    }

}
