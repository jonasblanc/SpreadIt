
public final class Adult extends Human {

    public Adult(int x, int y, Grid aera) {
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
