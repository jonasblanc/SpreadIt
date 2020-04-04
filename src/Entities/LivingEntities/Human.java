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
    
    
    private float infectionProbability;
    private float virusQuantity;//Dose of the virus in the human in percentage
    private float maxVirusQuantity;
    
    private Action currentAction= Action.STAY;
    private House home;
    
    public Human(int x, int y, Grid aera, float infectionProbability,float maxVirusQuantity, House h) {
        super(x, y, aera);
        this.infectionProbability=infectionProbability;
        this.maxVirusQuantity=maxVirusQuantity;
        this.virusQuantity=0;
        this.home = h;

    }
    
    public enum Action{
        GT_HOME, GT_HOSPITAL, STAY, GT_WORK, PLAY 
    }
    
    public void giveNewActions(Action a) {
        setCurrentAction(a);
        switch(a) {
        case GT_HOME: 
            super.setGoal(home.getPosX(), home.getPosY()); 
            break;
        case GT_HOSPITAL: 
            Hospital hospital = super.getGrid().getNearestHospital(super.getPosX(), super.getPosY());
            if(hospital != null) {
                super.setGoal(hospital.getPosX(), hospital.getPosY());
            }else {
                super.setGoal(home.getPosX(), home.getPosY());
                setCurrentAction(Action.GT_HOME);
            }
            break;
           
        case STAY: 
            break;
        default:
            specificAction(a);
        }
       
    }
    
    public abstract void specificAction(Action a);
    
    @Override
    public boolean isInfected() {
        return virusQuantity>TRESHOLD_TO_HEAL;
    }
    
    @Override
    public boolean infect(boolean forced) {
        
       //Generate random number between 0(inclusive) and 100 (exclusive)for probability 
       if(!isInfected() && (forced || ((float)new Random().nextInt(100))/100<infectionProbability)) {
           virusQuantity=maxVirusQuantity*INITIAL_INFECTION_FACTOR;
           System.out.println("A human got infected");
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
                System.out.println("A human died");
                removeEntity();
            }
        }
    }
    
    protected void setVirusQuantity(float qt) {
        virusQuantity=qt;
    }
    
    public Action getCurrentAction() {
        return currentAction;
    }
    
    public void setCurrentAction(Action a) {
        currentAction = a;
    }
    
}
