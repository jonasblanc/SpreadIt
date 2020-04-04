import java.util.Random;



public final class Child  extends Human {
    
    private final static float INFECTION_PROBABILITY=0.1f; 
    private final static float MAX_VIRUS_QUANTITY=100.0f; 
    private final static float VIRUS_INCREASE=1.05f; 

    public Child(int x, int y, Grid aera, boolean infected) {
        super(x, y, aera, INFECTION_PROBABILITY, MAX_VIRUS_QUANTITY);  
        if(infected) {
            infect(true);
        }
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public String toString() {
        return isInfected() ? "c":"C";
    }

    @Override
    public void update() { 
        globalMove();
        spreadInfection();
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

    /* 
     * 
     */
    /*@Override
    public void spreadInfection() {
        // TODO Auto-generated method stub 
    }*/

    /* 
     * 
     */
    @Override
    public void updateInfection() {
        if(isInfected()) {
            increaseVirus(VIRUS_INCREASE);
        }
        
    }

}
