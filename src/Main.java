
public final class Main {

    private final static float TIME_TRESHOLD = 500000000;
    
    public static void main(String[] args) {
        Grid g = new Grid(10, 10);
        Adult a = new Adult(5,5,g);
        g.getCell(5,5).addEntity(a);
        g.addEntity(a);
               
        long lastTime = System.nanoTime();
        while(true) {
            long currentTime = System.nanoTime();
            if(currentTime-lastTime > TIME_TRESHOLD) {
               g.update();
                System.out.println(g.toString());
                lastTime = currentTime;
            }
           
        }
        
       
    }

}
