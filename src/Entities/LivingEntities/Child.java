package Entities.LivingEntities;

import Entities.House;
import GameEnvironment.Grid;



public final class Child  extends Human {
    
    private final static float INFECTION_PROBABILITY=0.25f; 
    private final static float MAX_VIRUS_QUANTITY=100.0f; 
    private final static float VIRUS_INCREASE=1.05f; 

    public Child(int x, int y, Grid aera, boolean infected, House home) {
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
        return isInfected() ? "c":"C";
    }

    @Override
    public void update(int time) { 
        globalMove();
        spreadInfection();
        updateInfection();
        if(super.needToGoToHospital()) {
            super.giveNewActions(Action.GT_HOSPITAL);
        }else {
            switch(time) {
            case 90:
                super.giveNewActions(Action.PLAY);
                break;
            case 180:
                super.giveNewActions(Action.GT_HOME);
                break;
            }
        }
    }
 

    @Override
    public int getDistanceByMove() {
        return 1;
    }

    @Override
    public void moveWhenNotFollowingAGoal() {
        randomMove();
    }

    @Override
    public void goalAchived() {
       super.giveNewActions(Action.STAY);
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

    @Override
    public void specificAction(Action a) {
        switch(a) {
        case PLAY:
            super.setIsFollowingAGoal(false);
            super.setCurrentAction(Action.PLAY);
        }
        
    }

}
