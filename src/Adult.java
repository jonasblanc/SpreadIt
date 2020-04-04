
public final class Adult extends Human {

    public Adult(int x, int y, Grid aera) {
        super(x, y, aera);
        super.setGoal(9,9);
    }

    @Override
    public void getInfect() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public String toString() {
        return "A";
    }

    @Override
    public void update() {
        globalMove();
    }

    @Override
    public int getDistanceByMove() {
        return 1;
    }

    @Override
    public void moveWhenNotFollowingAGoal() {
       
        randomMove();
        /*
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
         */
    }

    @Override
    public void goalAchived(int x, int y) {
        //Do nothing for now
    }

}
