package Entities.LivingEntities;
import Entities.House;
import Entities.WorkPlace;
import GameEnvironment.Grid;

public final class Adult extends Human {
    
    private final static float INFECTION_PROBABILITY=0.5f; 
    private final static float MAX_VIRUS_QUANTITY=100.0f; 
    private final static float VIRUS_INCREASE=1.02f; 
    
    private final WorkPlace workPlace;
    
    
    public Adult(int x, int y, Grid aera, boolean infected, House home, WorkPlace wp) {
        super(x, y, aera, INFECTION_PROBABILITY, MAX_VIRUS_QUANTITY, home);  
        if(infected) {
            infect(true);
        }
        this.workPlace = wp;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public String toString() {
        return isInfected() ? "a":"A";
    }

    @Override
    public void update(int time) {
        globalMove();
        spreadInfection();
        updateInfection();
        
        if(super.needToGoToHospital()) {
            super.giveNewActions(Action.GT_HOSPITAL);
            System.out.println("Should go to hospital");
        }else {
            switch(time) {
            case 80:
                super.giveNewActions(Action.GT_WORK);
                System.out.println("GO WORK");
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
    public void goalAchieved() {
        super.giveNewActions(Action.STAY);
    }

    /*@Override
    public void spreadInfection() {}*/

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
        case GT_WORK:
            super.setGoal(workPlace.getPosX(), workPlace.getPosY()); break;
        default:
            break;
        }
        return;
    }

}
