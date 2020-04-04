package Entities.LivingEntities;
import java.util.Random;

import Entities.House;
import GameEnvironment.Grid;

public class Elder extends Human {
    
    private final static float INFECTION_PROBABILITY=0.5f; 
    private final static float MAX_VIRUS_QUANTITY=100.0f; 
    private final static float VIRUS_INCREASE=1.04f; 
    private final static int DISTANCE_PER_MOVE=1; 
    
    private final static int TIME_TO_STROLL=100;
    private final static int TIME_GO_HOME=170;

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
        
        move(); 
        spreadInfection();
        updateInfection();
        
        if(super.needToGoToHospital()) {
            super.giveNewAction(Action.GT_HOSPITAL);
        }else  if(!inHospital()) {
            switch(time) {
            case TIME_TO_STROLL:
                super.giveNewAction(Action.STROLL);
                break;
            case TIME_GO_HOME:
                super.giveNewAction(Action.GT_HOME);
                break;
            }
        }
    }

    @Override
    public int getDistancePerMove() {
        return DISTANCE_PER_MOVE;
    }

    @Override
    public void goalHasBeenReached() {
        
        if(getCurrentAction()==Action.GT_HOSPITAL) {
            super.giveNewAction(Action.STAY_AT_HOSPITAL);
        }
        
        super.giveNewAction(Action.STAY);
    }


    @Override
    public void updateInfection() {
        if(isInfected()) {
            increaseVirus(VIRUS_INCREASE);
        }
        
    }

    @Override
    public boolean giveSpecificAction(Action a) {
        switch(a) {
        case STROLL:{
            int x = new Random().nextInt(super.getGrid().getBorderX());
            int y = new Random().nextInt(super.getGrid().getBorderY());
            super.setGoalPosition(x, y);
            super.setCurrentAction(a); 
            return true;
            }
            default:{
                return false;
            }
        }
    }

    @Override
    public boolean moveSpecific(Action a) {
        switch(a) {
            case STROLL:{
                moveTowardsGoal();
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
