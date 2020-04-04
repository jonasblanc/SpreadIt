package Entities.LivingEntities;
import java.util.Random;
import java.util.Set;

import Entities.Entity;
import Entities.Infectable;
import Entities.MovableEntity;
import GameEnvironment.Grid;

public abstract class Human extends MovableEntity implements Infectable{

    private final static float TRESHOLD_TO_HEAL=1;
    
    private float infectionProbability;
    private float virusQuantity;//Dose of the virus in the human in percentage
    private float maxVirusQuantity;
    
    public Human(int x, int y, Grid aera, float infectionProbability,float maxVirusQuantity) {
        super(x, y, aera);
        this.infectionProbability=infectionProbability;
        this.maxVirusQuantity=maxVirusQuantity;
        this.virusQuantity=0;

    }
    
    @Override
    public boolean isInfected() {
        return virusQuantity>TRESHOLD_TO_HEAL;
    }
    
    @Override
    public boolean infect(boolean forced) {
        
       //Generate random number between 0(inclusive) and 100 (exclusive)for probability 
       if(forced || ((float)new Random().nextInt(100))/100<infectionProbability) {
           virusQuantity=maxVirusQuantity/2;
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
            Set <Entity> adjacentEntities=  getCurrCell().getRadiusEntities(1);
            adjacentEntities.remove(this);
            for(Entity e:adjacentEntities) {
                if(e instanceof Infectable) {
                    ((Infectable) e).infect(false);
                }
            }
        }
    }
    
    /**
     * Updates the virus dose in the individual
     */
    public abstract void updateInfection();
       
    
    
    public boolean isDead() {
        return virusQuantity>=maxVirusQuantity;
    }
    
    /**
     * @param scale: the scale by which the virus is increased (if scale<1 => the virus is decreased) 
     */
    public void increaseVirus(float scale) {
        virusQuantity=virusQuantity*scale;
        
        if(isDead()) {
            System.out.println("A human died");
            removeEntity();
        }
    }
    
    protected void setVirusQuantity(float qt) {
        virusQuantity=qt;
    }
    
}
