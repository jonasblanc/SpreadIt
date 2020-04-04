import java.util.Random;

public final class Child  extends Human {

    public Child(int x, int y, Grid aera) {
        super(x, y, aera);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public String toString() {
        return "C";
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
        int x = new Random().nextInt(super.getGrid().getBorderX());    
        int y = new Random().nextInt(super.getGrid().getBorderX());    
        setGoal(x,y);
    }

    @Override
    public void goalAchived(int x, int y) {
        //Do nothing yet
    }

    @Override
    public void getInfect() {
        // TODO Auto-generated method stub
        
    }
}
