import java.util.ArrayList;
import java.util.List;
import java.util.Random;

private final int STATS_BOARD_HEIGHT = 200;
private final static Random r = new Random();

private final static int MIN_HOUSE = 5;
private final static int MAX_HOUSE = 10;

private final static int MIN_WORK = 2;
private final static int MAX_WORK = 5;

private final static int MIN_HOSPITAL = 1;
private final static int MAX_HOSPITAL = 2;

private final static int MAX_C_PER_HOME = 3;
private final static int MAX_A_PER_HOME = 2;
private final static int MAX_E_PER_HOME = 2;

private final static int MIN_HOSPITAL_CAPACITY = 2;
private final static int MAX_HOSPITAL_CAPACITY = 5;

//TODO faire dépendre ça du nombre de personne dans l'hopital
private final static int MIN_HOSPITAL_POWER = 25;
private final static int MAX_HOSPITAL_POWER = 26;

private final static int MIN_A_INFECTED = 1;
private final static int MAX_A_INFECTED = 2;

private final static int MIN_C_INFECTED = 1;
private final static int MAX_C_INFECTED = 3;

private final static int MIN_E_INFECTED = 1;
private final static int MAX_E_INFECTED = 3;

private final static float TIME_TRESHOLD = 100000000;

private final static int CELL_SIZE = 40;
private final color C_ADULT = color(255,0,0);
private final color C_CHILD = color(0,204,255);
private final color C_ELDER = color(204,0,204);

private final color C_HOSPITAL = color(0,255,0);
private final color C_HOUSE = color(230,172,0);
private final color C_WORKPLACE = color(100,100,100);
private final color C_WALL = color(25,25,25);
private final color C_INFECTED = color(255,255,0);
private final color C_BACKGROUND = color(0,0,0);

private final int HUMAN_DIAMETER = 40;
private final int ADULT_DIAMETER = HUMAN_DIAMETER;
private final int ELDER_DIAMETER = HUMAN_DIAMETER;
private final int CHILD_DIAMETER = HUMAN_DIAMETER;
private final int HUMAN_RADIUS = HUMAN_DIAMETER/2;

PGraphics gameSurface;
//PGraphics stats;
Grid grid;
int time;
int day;
//int initialEntitiesCount = grid.howManyHuman();
long lastTime;

void settings() {
  size(800, 1000, P2D);
}


/* kind of constructor for game */
void setup() {
  gameSurface= createGraphics(width, height, P2D);
  //stats = createGraphics(width,STATS_BOARD_HEIGHT, P2D);
  grid = new Grid(20, 20);
  generateMap(grid);
  time = 0;
  day = 0;
  lastTime= System.nanoTime();
}

/* each frames draw scene */
void draw() {

  long currentTime = System.nanoTime();
  if (currentTime-lastTime > TIME_TRESHOLD) {
    //printStat(grid, initialEntitiesCount, day, time);
    grid.draw(gameSurface);
    image(gameSurface, 0, 0);
    grid.update(time);
    lastTime = currentTime;
    time ++;
    if (time == 241) {
      time = 0;
      day++;
    }
  } 
  //drawStats();
  //image(stats, 0, height - STATS_BOARD_HEIGHT);
}

private void generateMap(Grid grid) {
  List<House> houses = generateHouses(grid);
  System.out.println("Houses generated");

  List<WorkPlace> workPlaces = generateWorkPlaces(grid);
  System.out.println("WorkPlaces generated");

  generateHospitals(grid);
  System.out.println("Hospitals generated");

  addChildren(houses, grid);

  addElders(houses, grid);

  addAdults(houses, workPlaces, grid);

  addInfectedAdults(houses, workPlaces, grid);
  //addInfectedChildren(houses, grid);
  //addInfectedElder(houses, grid);
}

private int rd(int x) {
  return r.nextInt(x+1);
}

private int rd(int min, int max) {
  return r.nextInt(max -min) + min;
}

private boolean isPosFree(int x, int y, Grid grid) {
  Cell c = grid.getCell(x, y);
  return !c.hasHospital() && !c.hasHouse() && !c.hasWorkPlace();
}

private List<House> generateHouses(Grid grid) {
  int nbrHouse = rd(MIN_HOUSE, MAX_HOUSE);
  List<House> houses = new ArrayList();
  for (int i = 0; i < nbrHouse; ++i) {
    int x;
    int y;
    do {
      x = rd(grid.getBorderX()-1);
      y = rd(grid.getBorderY()-1);
    } while (!isPosFree(x, y, grid));

    House h = new House(x, y, grid, MAX_C_PER_HOME + MAX_A_PER_HOME + MAX_E_PER_HOME);
    houses.add(h);
    grid.getCell(x, y).addEntity(h);
    grid.addEntity(h);
  }
  return houses;
}

private void generateHospitals(Grid grid) {
  int nbrHospital = rd(MIN_HOSPITAL, MAX_HOSPITAL);
  for (int i = 0; i < nbrHospital; ++i) {
    int x;
    int y;
    do {
      x = rd(grid.getBorderX()-1);
      y = rd(grid.getBorderY()-1);
    } while (!isPosFree(x, y, grid));

    Hospital h = new Hospital(x, y, grid, rd(MIN_HOSPITAL_CAPACITY, MAX_HOSPITAL_CAPACITY), rd(MIN_HOSPITAL_POWER, MAX_HOSPITAL_POWER));
    grid.getCell(x, y).addEntity(h);
    grid.addEntity(h);
  }
}

private List<WorkPlace> generateWorkPlaces(Grid grid) {
  int nbrWork = rd(MIN_WORK, MAX_WORK);
  List<WorkPlace> works = new ArrayList();
  for (int i = 0; i < nbrWork; ++i) {
    int x;
    int y;
    do {
      x = rd(grid.getBorderX()-1);
      y = rd(grid.getBorderY()-1);
    } while (!isPosFree(x, y, grid));

    WorkPlace wp = new WorkPlace(x, y, grid, MAX_HOUSE * MAX_A_PER_HOME);
    works.add(wp);
    grid.getCell(x, y).addEntity(wp);
    grid.addEntity(wp);
  }
  return works;
}

private void addChildren(List<House> houses, Grid grid) {
  for (House h : houses) {
    int x = h.getPosX();
    int y = h.getPosY();
    int num = rd(MAX_C_PER_HOME);
    for (int i = 0; i < num; ++i) {
      Child c = new Child(x, y, grid, false, h);
      grid.getCell(x, y).addEntity(c);
      grid.addEntity(c);
    }
  }
}

private void addElders(List<House> houses, Grid grid) {
  for (House h : houses) {
    int x = h.getPosX();
    int y = h.getPosY();
    int num = rd(MAX_E_PER_HOME);
    for (int i = 0; i < num; ++i) {
      Elder e = new Elder(x, y, grid, false, h);
      grid.getCell(x, y).addEntity(e);
      grid.addEntity(e);
    }
  }
}

private void addAdults(List<House> houses, List<WorkPlace> workPlaces, Grid grid) {
  for (House h : houses) {
    int x = h.getPosX();
    int y = h.getPosY();
    int num = rd(MAX_A_PER_HOME);
    for (int i = 0; i < num; ++i) {
      WorkPlace wp = workPlaces.get(rd(workPlaces.size()-1));
      Adult a = new Adult(x, y, grid, false, h, wp);
      grid.getCell(x, y).addEntity(a);
      grid.addEntity(a);
    }
  }
}

private void addInfectedAdults(List<House> houses, List<WorkPlace> workPlaces, Grid grid) {


  int numA = rd(MIN_A_INFECTED, MAX_A_INFECTED);
  for (int i = 0; i < numA; ++i) {
    House h = houses.get(rd(houses.size()-1));
    int x = h.getPosX();
    int y = h.getPosY();
    WorkPlace wp = workPlaces.get(rd(workPlaces.size()-1));
    Adult a = new Adult(x, y, grid, true, h, wp);
    grid.getCell(x, y).addEntity(a);
    grid.addEntity(a);
  }
}

private void addInfectedChildren(List<House> houses, Grid grid) {

  int numC = rd(MIN_C_INFECTED, MAX_C_INFECTED);
  for (int i = 0; i < numC; ++i) {
    House h = houses.get(rd(houses.size()-1));
    int x = h.getPosX();
    int y = h.getPosY();
    Child c = new Child(x, y, grid, true, h);
    grid.getCell(x, y).addEntity(c);
    grid.addEntity(c);
  }
}

private void addInfectedElder(List<House> houses, Grid grid) {

  int numE = rd(MIN_E_INFECTED, MAX_E_INFECTED);
  for (int i = 0; i < numE; ++i) {
    House h = houses.get(rd(houses.size()-1));
    int x = h.getPosX();
    int y = h.getPosY();
    Elder e = new Elder(x, y, grid, true, h);
    grid.getCell(x, y).addEntity(e);
    grid.addEntity(e);
  }
}
