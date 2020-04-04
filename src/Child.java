
public final class Child  extends Human {

    public Child(int x, int y, Grid aera) {
        super(x, y, aera);
    }

    @Override
    public void getInfect() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean takeCellSpace() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update() { 
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
