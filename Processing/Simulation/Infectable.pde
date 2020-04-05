public interface Infectable {

    /**
     * 
     * @return: returns whether the entity is infected
     */
    abstract boolean isInfected();
    
    /**
     * Infects the infectable with a certain probability
     */
    abstract boolean infect(boolean forced);
    
    /**
     * Let the infectable spread the infection to adjacent cells
     */
    abstract void spreadInfection();
    
}
