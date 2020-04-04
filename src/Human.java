import java.util.Random;

public abstract class Human extends MovableEntity implements Infectable{

    private final float tresholdToHeal=1;
    

    private float infectionProbability;
    private float virusQuantity;//Dose of the virus in the human in percentage
    private float maximumVirulence;
    private float maxVirusQuantity;
    
    public Human(int x, int y, Grid aera, float infectionProbability,float maxVirusQuantity) {
        super(x, y, aera);
        this.infectionProbability=infectionProbability;
        this.virusQuantity=0;
        this.maxVirusQuantity=maxVirusQuantity;
        this.virusQuantity=virusQuantity;
        
    }
    
    @Override
    public boolean isInfected() {
        return virusQuantity<tresholdToHeal;
    }
    
    @Override
    public boolean infect(boolean forced) {
        
       
       //Generate random number between 0(inclusive) and 100 (exclusive)for probability 
       if(forced || new Random().nextInt(100)<infectionProbability) {
           virusQuantity=maximumVirulence/4;
       }
       return isInfected();
    }
    
    /* 
     * With a certain probability spreads the infection to neighbouring individuals
     */
    @Override
    public abstract void spreadInfection();
    
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
            
        }
    }
    
    protected void setVirusQuantity(float qt) {
        virusQuantity=qt;
    }
    
}
