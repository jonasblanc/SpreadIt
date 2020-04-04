
import Entities.House;
import Entities.WorkPlace;
import Entities.LivingEntities.Adult;
import Entities.LivingEntities.Child;
import GameEnvironment.Grid;

public final class Main {

    private final static float TIME_TRESHOLD = 100000000;
    
    public static void main(String[] args) {
        
        Grid g = new Grid(10, 10);
        
        WorkPlace wk = new WorkPlace(5,4,g,6);
        g.getCell(5,4).addEntity(wk);
        g.addEntity(wk);
        
        
        House h = new House(7,7,g,5);
        g.getCell(7,7).addEntity(h);
        g.addEntity(h);
        
        Adult a = new Adult(7,7,g,false, h, wk);
        g.getCell(7,7).addEntity(a);
        g.addEntity(a);
        
        Child c1 = new Child(7,7,g,true, h);
        g.getCell(7,7).addEntity(c1);
        g.addEntity(c1);
        
        
        House h1 = new House(0,9,g,5);
        g.getCell(0,9).addEntity(h1);
        g.addEntity(h1);
        
        Adult a2 = new Adult(0,9,g,false, h1, wk);
        g.getCell(0,9).addEntity(a2);
        g.addEntity(a2);
        
        Child c2 = new Child(0,9,g,false, h1);
        g.getCell(1,2).addEntity(c2);
        g.addEntity(c2);
        
        long lastTime = System.nanoTime();
        int time = 0;
        while(true) {
            
            long currentTime = System.nanoTime();
            if(currentTime-lastTime > TIME_TRESHOLD) {
                System.out.println(g.toString());
                g.update(time);
                lastTime = currentTime;
            }
            time ++;
            if(time == 241) {
                time = 0;
            }
           
        }
    }

}
