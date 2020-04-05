public final class Adult extends Human {
    
    private final static float INFECTION_PROBABILITY=0.5f; 
    private final static float MAX_VIRUS_QUANTITY=100.0f; 
    private final static float VIRUS_INCREASE=1.02f; 
    private final static int DISTANCE_PER_MOVE=1; 
    private final static int TIME_TO_WORK=80;
    private final static int TIME_GO_HOME=180;
    
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
        move();
        spreadInfection();
        updateInfection();
        
        if(super.needToGoToHospital()) {
            super.giveNewAction(Action.GT_HOSPITAL);
        }
        else if(!inHospital()) {
            switch(time) {
            case TIME_TO_WORK:
                super.giveNewAction(Action.GT_WORK);
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
        case GT_WORK:{
            super.setGoalPosition(workPlace.getPosX(), workPlace.getPosY()); 
            super.setCurrentAction(a); 
            return true;  
        }
        
        default:
            return false;
        }
    }
    
    @Override
    public boolean moveSpecific(Action a) {
        switch(a) {
            case GT_WORK:{
                moveTowardsGoal();
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public void draw(PGraphics gameSurface){
      if(isInfected()){
         gameSurface.stroke(C_INFECTED);
      }else{
        gameSurface.noStroke();
      }
      gameSurface.fill(C_ADULT);
      gameSurface.circle(0,0,ADULT_DIAMETER);
    }

}
