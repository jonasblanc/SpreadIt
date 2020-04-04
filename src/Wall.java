
public class Wall extends Entity{

    /**
     * 
     * @param x
     * @param y
     * @param area
     */
    public Wall(int x, int y, Grid area) {
        super(x, y, area);
    }


    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public String toString() {
        return "W";
    }


    @Override
    public void update() {
    }

}
