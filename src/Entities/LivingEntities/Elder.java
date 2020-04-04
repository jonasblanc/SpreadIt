package Entities.LivingEntities;
import java.util.Random;

import Entities.House;
import Entities.LivingEntities.Human.Action;
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
        
        if(super.needToGoToHospital()) {
            super.giveNewActions(Action.GT_HOSPITAL);
        }else  if(!super.isInfected()) {
            switch(time) {
            case 100:
                super.giveNewActions(Action.STROLL);
                break;
            case 170:
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
    public void goalAchieved() {
        super.giveNewActions(Action.STAY);
    }


    @Override
    public void updateInfection() {
        if(isInfected()) {
            increaseVirus(VIRUS_INCREASE);
        }
        
    }

    @Override
    public void specificAction(Action a) {
        switch(a) {
        case STROLL:
            int x = new Random().nextInt(super.getGrid().getBorderX());
            int y = new Random().nextInt(super.getGrid().getBorderY());
            super.setGoal(x, y); 
            break;
        }
        
    }

}
