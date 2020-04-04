package Entities;

import java.util.Random;

import GameEnvironment.Cell;
import GameEnvironment.Grid;

public abstract class MovableEntity extends Entity {
   
    private static final int NUMBER_TRIAL_RANDOM_MOVE = 5;
    
    private Dir direction;
    private int goalX;
    private int goalY;

    public MovableEntity(int x, int y, Grid aera) {
        super(x, y, aera);
        goalX = x;
        goalX = y; 
        this.direction=Dir.LEFT;
    }
        
    /**
     * @return the number of steps a movable entity can execute in one turn
     */
    public abstract int getDistancePerMove();
            
    /**
     * Method that is called when a goal has been reached
     */
    public abstract void goalHasBeenReached();
    
    //Might be useless
    /**
     * @return true if the movable entity needs to move to reach a goal
     */
    //public abstract boolean needsToReachGoal();
    

    public static enum Dir{
        UP, DOWN, LEFT, RIGHT
    }
    
    /**
     * Moves the entity towards its goal by number of steps it is allowed in a move
     * Must be called by Update
     */
    public void moveTowardsGoal() {
        for(int i = 0; i <getDistancePerMove(); ++i) {
            moveOneStepTowardsGoal();
            if(hasReachedGoal()) {
                goalHasBeenReached();
                return;
            }  
        }
    }
    
    /**
     * Move to x,y if permitted
     * @param x 
     * @param y
     * @return if the move was possible
     */
    private boolean moveTo(int x, int y) {
        if(super.getGrid().isInBorder(x, y)) {
            Cell oldC = super.getCurrCell();
            Cell newC = super.getGrid().getCell(x, y);
        
            if(oldC.canLeave(this) && newC.canEnter(this)) {
                oldC.removeEntity(this);
                newC.addEntity(this);
                super.setCurrCell(newC);
                super.setPosX(x);
                super.setPosY(y);
                return true;
            }
            return false;
        }
        return false;
    }
    
    /**
     * Move toward goal 
     * @return true if a move was made
     */
    private boolean moveOneStepTowardsGoal() {     
        System.out.println("Move one step towards goal at coordinates:(x,y)=(" +getGoalX()+ ","+getGoalY()+")");
        int distRemainingX = Math.abs(goalX - super.getPosX());
        int distRemainingY = Math.abs(goalY - super.getPosY());
        if(distRemainingX == 0 && distRemainingY ==0) {
            return false;
        }
        
        boolean hasMoved = false;
        if(distRemainingX > distRemainingY) {
            if(goalX > super.getPosX()) {
                direction = Dir.RIGHT;//TODO:DELETE
                hasMoved= moveTowardDir(Dir.RIGHT);
            }else if(goalX <  super.getPosX()){
                direction = Dir.LEFT;//TODO:DELETE
                hasMoved= moveTowardDir(Dir.LEFT);
            }
        } 
        
        if(!hasMoved && distRemainingY!=0) {
            if(goalY > super.getPosY()) {
                direction = Dir.UP;//TODO:DELETE
                hasMoved= moveTowardDir(Dir.UP);
            }else if(goalY < super.getPosY()){
                direction = Dir.DOWN;//TODO:DELETE
                hasMoved= moveTowardDir(Dir.DOWN);
            }
        }
        return hasMoved;
    }
    
    /**
     * Set a goal
     * @param x
     * @param y
     * @return true if goal location in the grid
     */
    public boolean setGoalPosition(int x, int y) {
        if(super.getGrid().isInBorder(x, y)) {
           goalX = x;
           goalY = y;
           return true;
        }
       return false;
     }
    
    public int getGoalX() {
        return goalX;
    }
    
    public int getGoalY() {
        return goalY;
    }
    
    
    /**
     * Move along the direction
     * @return
     */
    private boolean moveTowardDir(Dir direction) {
        switch(direction) {
        case UP:    return moveTo(super.getPosX(), super.getPosY()+1);
        case DOWN:  return moveTo(super.getPosX(), super.getPosY()-1);
        case LEFT:  return moveTo(super.getPosX()-1, super.getPosY());
        case RIGHT: return moveTo(super.getPosX()+1, super.getPosY());
        default: return false;
        }
    }
    
    /**
     * Move in a random direction
     * @return true if movement was a success
     */
    public boolean randomMove() {
        boolean hasMoved = false;
        int numberOfTrial = NUMBER_TRIAL_RANDOM_MOVE;
        while(!hasMoved && numberOfTrial > 0) {
            int i = new Random().nextInt(Dir.values().length);
            Dir direction = Dir.values()[i];
            hasMoved = moveTowardDir(direction);
            this.direction=direction;//TODO:DELETE
        }
       return hasMoved;
    }
    
    public Dir getDirection() {
        return direction;
    }

    public void setDirection(Dir direction) {
        this.direction = direction;
    }
    
    /**
     * @return true if the entity is on its goal position
     */
    public boolean hasReachedGoal() {
        return goalX==super.getPosX() && goalY ==super.getPosY();
    }
}
