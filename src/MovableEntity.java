import java.util.Random;

public abstract class MovableEntity extends Entity {
   
    private static final int NUMBER_TRIAL_RANDOM_MOVE = 5;
    
    private Dir direction = Dir.LEFT;
    private int goalX;
    private int goalY;
    private boolean isFollowingAGoal = false;

    public MovableEntity(int x, int y, Grid aera) {
        super(x, y, aera);
        goalX = x;
        goalX = y; 
    }
        
    public abstract int getDistanceByMove();
        
    public abstract void moveWhenNotFollowingAGoal();
    
    public abstract void goalAchived(int x, int y);
    

    public static enum Dir{
        UP, DOWN, LEFT, RIGHT
    }    
    
    /**
     * Move the entity depending on if the entity is following a goal or not
     * Must be called by Update
     */
    public void globalMove() {
        for(int i = 0; i <getDistanceByMove(); ++i) {
            if(isFollowingAGoal) {
                if(moveTowardGoal()) {
                    isFollowingAGoal = false;
                    goalAchived(goalX, goalY);
                    return;
                }
            }else {
                moveWhenNotFollowingAGoal();
            }
            
        }
    }
    
    
    /**
     * Move to x,y if permitted
     * @param x 
     * @param y
     * @return if the move was possible
     */
    public boolean moveTo(int x, int y) {
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
     * Move toward goal (if not goal set, move toward birth place)
     * @return true if already reached the goal
     */
    public boolean moveTowardGoal() {        
        int absDiffX = Math.abs(goalX - super.getPosX());
        int absDiffY = Math.abs(goalY - super.getPosY());
        if(absDiffX == 0 && absDiffY ==0) {
            return true;
        }
        
        boolean hasMoved = false;
        if(absDiffX > absDiffY) {
            if(goalX - super.getPosX() > 0) {
                direction = Dir.RIGHT;
                hasMoved= moveTowardDir();
            }else if(absDiffX != 0){
                direction = Dir.LEFT;
                hasMoved= moveTowardDir();
            }
        } 
        
        if(!hasMoved) {
            if(goalY - super.getPosY() > 0) {
                direction = Dir.UP;
                hasMoved= moveTowardDir();
            }else if(absDiffY != 0){
                direction = Dir.DOWN;
                hasMoved= moveTowardDir();
            }
        }
        return false;
    }
    
    /**
     * Set a goal
     * @param x
     * @param y
     * @return true if location in the grid
     */
    public boolean setGoal(int x, int y) {
        if(super.getGrid().isInBorder(x, y)) {
           goalX = x;
           goalY = y;
           isFollowingAGoal = true;
           return true;
        }
       return false;
     }
    
    /**
     * Move along the direction
     * @return
     */
    public boolean moveTowardDir() {
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
            direction = Dir.values()[i];
            hasMoved = moveTowardDir();
        }
       return hasMoved;
    }
    
    public Dir getDirection() {
        return direction;
    }

    public void setDirection(Dir direction) {
        this.direction = direction;
    }

}
