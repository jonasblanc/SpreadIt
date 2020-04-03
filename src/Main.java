
public final class Main {

    private final static float TIME_TRESHOLD = 500000000;
    
    public static void main(String[] args) {
        Grid g = new Grid(10, 10);
        Entity h = new HealtyEntity(5,5,g,true);
        g.getCell(5,5).addEntity(h);
        Entity i = new InfectedEntity(7,7,g,true);
        g.getCell(7,7).addEntity(i);
        
        long lastTime = System.nanoTime();
        while(true) {
            long currentTime = System.nanoTime();
            if(currentTime-lastTime > TIME_TRESHOLD) {
                h.update();
                i.update();
                System.out.println(g.toString());
                lastTime = currentTime;
            }
           
        }
        
       
    }

}
