
public final class Main {

    private final static float TIME_TRESHOLD = 500000000;
    
    public static void main(String[] args) {
        Grid g = new Grid(10, 10);
        HealtyEntity h = new HealtyEntity(5,5,g,true);
        g.getCell(5,5).addEntity(h);
        g.addEntity(h);
        Entity i = new InfectedEntity(7,7,g,true);
        g.getCell(7,7).addEntity(i);
        g.addEntity(i);
        
        boolean firstTime = true;
        
        long lastTime = System.nanoTime();
        while(true) {
            long currentTime = System.nanoTime();
            if(currentTime-lastTime > TIME_TRESHOLD) {
               g.update();
                System.out.println(g.toString());
                lastTime = currentTime;
                
                if(firstTime) {
                    h.getInfect();
                    firstTime = false;
                }
            }
           
        }
        
       
    }

}
