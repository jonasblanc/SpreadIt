
public abstract class MovableEntity extends Entity {
   
    
    private Dir direction = Dir.LEFT;

    public MovableEntity(int x, int y, Grid aera, boolean takeCellSpace, String printChar) {
        super(x, y, aera, takeCellSpace, printChar);
    }
    
    public Dir getDirection() {
        return direction;
    }

    public void setDirection(Dir direction) {
        this.direction = direction;
    }

    public static enum Dir{
        UP, DOWN, LEFT, RIGHT
    }    
    
    public boolean moveTo(int x, int y) {
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
    
    public abstract void getInfect();
}
