package Entities.LivingEntities;
import GameEnvironnement.Grid;

public class Elder extends Human {
    
    private final static float INFECTION_PROBABILITY=0.5f; 
    private final static float MAX_VIRUS_QUANTITY=100.0f; 
    private final static float VIRUS_INCREASE=1.25f; 

    public Elder(int x, int y, Grid aera,boolean infected) {
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
        return isInfected() ?"e":"E";
    }

    @Override
    public void update() {
        globalMove();  
        spreadInfection();
        updateInfection();
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

    /*
    @Override
    public void spreadInfection() {}
    */
    
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
