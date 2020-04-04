package Entities.LivingEntities;
import Entities.House;
import GameEnvironment.Grid;

public class Elder extends Human {
    
    private final static float INFECTION_PROBABILITY=0.5f; 
    private final static float MAX_VIRUS_QUANTITY=100.0f; 
    private final static float VIRUS_INCREASE=1.04f; 

    public Elder(int x, int y, Grid aera,boolean infected, House home) {
        super(x, y, aera, INFECTION_PROBABILITY, MAX_VIRUS_QUANTITY, home);  
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
    public void update(int time) {
        globalMove();  
        spreadInfection();
        updateInfection();
    }

    @Override
    public int getDistanceByMove() {
        return 1;
    }

    @Override
    public void goalAchieved() {
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

    @Override
    public void specificAction(Action a) {
        // TODO Auto-generated method stub
        
    }

}
