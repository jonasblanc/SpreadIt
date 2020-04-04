
public class Elder extends Human {

    public Elder(int x, int y, Grid aera) {
        super(x, y, aera);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public String toString() {
        return "E";
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
        //Doesn't move when no goal
    }

    @Override
    public void goalAchived(int x, int y) {
        // TODO Auto-generated method stub
    }

}
