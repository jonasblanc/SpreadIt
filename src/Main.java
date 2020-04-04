
public final class Main {

    private final static float TIME_TRESHOLD = 500000000;
    
    public static void main(String[] args) {
        Grid g = new Grid(10, 10);
        Adult a = new Adult(5,5,g);
        g.getCell(5,5).addEntity(a);
        g.addEntity(a);
        
        Elder e = new Elder(5,5,g);
        g.getCell(5,5).addEntity(e);
        g.addEntity(e);
        
        Child c1 = new Child(1,1,g);
        g.getCell(1,1).addEntity(c1);
        g.addEntity(c1);
        
        Child c2 = new Child(1,2,g);
        g.getCell(1,2).addEntity(c2);
        g.addEntity(c2);
        
        Child c3 = new Child(2,2,g);
        g.getCell(2,2).addEntity(c3);
        g.addEntity(c3);
        
        Child c4 = new Child(3,3,g);
        g.getCell(3,3).addEntity(c4);
        g.addEntity(c4);
                      
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
