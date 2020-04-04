package Entities;
import Entities.LivingEntities.Human;
import GameEnvironment.Grid;

public final class Hospital extends Buildings {

    //In this case, the worst hospitals do not modify the 
    public final static int MAX_HEAL_POWER = 100;
    public final static int MIN_HEAL_POWER = 0;
    private float healPower;
    
    public Hospital(int x, int y, Grid area, int capacity, float healPower){
        super(x, y, area, capacity);
        this.healPower = constrainHealPower(healPower);
    }
    
    @Override
    public boolean takeCellSpace() {
        return isFull();
    }

    @Override
    public String toString() {
        return "+";
    }

    @Override
    public void update(int time) {
        
        for( Human human : getCurrCell().getHumans()) {
            human.increaseVirus(1.0f-(float)healPower/MAX_HEAL_POWER);
        }
    }
    
    public static float constrainHealPower(float healPower) {
        
        return Math.max(Math.min(healPower, MAX_HEAL_POWER), MIN_HEAL_POWER);
        
    }
    

}
