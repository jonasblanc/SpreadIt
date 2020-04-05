package Entities.LivingEntities;
import java.util.Random;
import java.util.Set;

import Entities.Hospital;
import Entities.House;
import Entities.Infectable;
import Entities.MovableEntity;
import GameEnvironment.Grid;

public abstract class Human extends MovableEntity implements Infectable{

    private final static float TRESHOLD_TO_HEAL=1;
    private final static float SYMPTOM_DETECTION_TRESHOLD= 0.25f;
    private final static float INITIAL_INFECTION_FACTOR = 0.10f;
    
    private boolean hasBeenInfected;
    
    private float infectionProbability;
    private float virusQuantity;//Dose of the virus in the human in percentage
    private float maxVirusQuantity;
    
    private Action currentAction;
    private House home;
    
    public Human(int x, int y, Grid aera, float infectionProbability,float maxVirusQuantity, House h) {
        super(x, y, aera);
        this.infectionProbability=infectionProbability;
        this.maxVirusQuantity=maxVirusQuantity;
        this.virusQuantity=0;
        this.home = h;
        this.hasBeenInfected=false;
        this.currentAction = Action.STAY;

    }
    
    public enum Action{
        GT_HOME, GT_HOSPITAL, STAY_AT_HOSPITAL, STAY, GT_WORK, PLAY, STROLL
    }
    
    public abstract boolean giveSpecificAction(Action a);
    public abstract boolean moveSpecific(Action a);
    
    protected boolean move() {
        
        switch (currentAction) {
            case GT_HOME: {
                super.moveTowardsGoal();
                return true;
                
            }
            case GT_HOSPITAL:{
                
                System.out.println("Going to hospital at coordinates:(x,y)=(" +getGoalX()+ ","+getGoalY()+")");
                System.out.println("My coordinates are:(x,y)=(" +getPosX()+ ","+getPosY()+")");
                
                super.moveTowardsGoal();
                return true;
            }
            
            case STAY:{//do nothing by default
                return true;
            }
            
            default:{
                return moveSpecific (currentAction);
            }
        }   
    }
    
    /**
     * Give to the human a new action to do, it might change its goal coordinates accordingly
     * @param a: the action to be given
     * @return true if the action was made possible, otherwise, returns false and its current action remains unchanged
     */
    public boolean giveNewAction(Action a) {
        switch(a) {
            case GT_HOME:{ 
                super.setGoalPosition(home.getPosX(), home.getPosY()); 
                setCurrentAction(a);
                return true;
            }
                
            case GT_HOSPITAL: {
                Hospital hospital = super.getGrid().getNearestHospital(super.getPosX(), super.getPosY());
                System.out.println("New action: I need to go to the hospital");
                if(hospital != null) {
                    System.out.println("Hospital was found at coordinates (x,y)=("+hospital.getPosX()+","+hospital.getPosY()+")");
                    super.setGoalPosition(hospital.getPosX(), hospital.getPosY());
                    setCurrentAction(a);
                    return true;
                }
            
                else {
                    return false;
                }
            }
               
            case STAY:
            case STAY_AT_HOSPITAL:{
                setCurrentAction(a);
                return true;
            }
                
            default:{//Delegate the execution of such an action to subclass
                return giveSpecificAction(a);  
            }
        }
       
    }
    
    
    
    @Override
    public boolean isInfected() {
        return virusQuantity>TRESHOLD_TO_HEAL;
    }
    
    @Override
    public boolean infect(boolean forced) {
        
       //Generate random number between 0(inclusive) and 100 (exclusive)for probability 
       if(canBeInfected()  && (forced || ((float)new Random().nextInt(100))/100<infectionProbability)) {
           virusQuantity=maxVirusQuantity*INITIAL_INFECTION_FACTOR;
           hasBeenInfected=true;
       }
       return isInfected();
    }
    
    /* 
     * With a certain probability spreads the infection to neighbouring individuals
     */
    
    @Override
    public void spreadInfection () {
        if(isInfected()){
            Set <Infectable> adjacentInfectables=getCurrCell().getRadiusInfectables(1);
            adjacentInfectables.remove(this);
            for(Infectable e:adjacentInfectables) {
                    e.infect(false);
                }
            }
        }
    
    
    /**
     * Updates the virus dose in the individual
     */
    public abstract void updateInfection();
    
    public boolean needToGoToHospital() {
        return hasSymptom() && !isOrIsGoingHospital();
    }
       
    public boolean hasSymptom() {
        return virusQuantity>= maxVirusQuantity*SYMPTOM_DETECTION_TRESHOLD;
    }
    
    public boolean isDead() {
        return virusQuantity>=maxVirusQuantity;
    }
    
    public boolean isOrIsGoingHospital() {
        return super.getCurrCell().hasHospital() || currentAction == Action.GT_HOSPITAL;
    }
    
    /**
     * @param scale: the scale by which the virus is increased (if scale<1 => the virus is decreased) 
     */
    public void increaseVirus(float scale) {
        if(isInfected()) {
            virusQuantity=virusQuantity*scale;
            
            if(isDead()) {
                removeEntity();
            }
        }
    }
    
    public Action getCurrentAction() {
        return currentAction;
    }
    
    public void setCurrentAction(Action a) {
        currentAction = a;
    }
    
    public boolean isImmune() {
        return hasBeenInfected && !isInfected();
    }
    
    public boolean canBeInfected() {
        return !isImmune() && !isInfected();
    }
  
    public boolean inHospital() {
        return currentAction==Action.STAY_AT_HOSPITAL;
    }
    
    
}
