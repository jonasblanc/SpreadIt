
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Entities.Hospital;
import Entities.House;
import Entities.WorkPlace;
import Entities.LivingEntities.Adult;
import Entities.LivingEntities.Child;
import Entities.LivingEntities.Elder;
import GameEnvironment.Cell;
import GameEnvironment.Grid;

public final class Main {

	// ============================== Simulation parameters ==============================//

	private final static int GRID_WIDTH = 40;
	private final static int GRID_HEIGHT = 20;

	// Range for number of houses
	private final static int MIN_HOUSE = 5;
	private final static int MAX_HOUSE = 10;

	// Houses' capacity
	private final static int MAX_C_PER_HOME = 3;
	private final static int MAX_A_PER_HOME = 2;
	private final static int MAX_E_PER_HOME = 2;

	// Range for number of workplaces
	private final static int MIN_WORK = 2;
	private final static int MAX_WORK = 5;

	// Range for number of hospitals
	private final static int MIN_HOSPITAL = 1;
	private final static int MAX_HOSPITAL = 2;

	// Hospitals' capacity
	private final static int MIN_HOSPITAL_CAPACITY = 2;
	private final static int MAX_HOSPITAL_CAPACITY = 5;

	// Healing factor for hospitals
	private final static int MIN_HOSPITAL_POWER = 25;
	private final static int MAX_HOSPITAL_POWER = 26;

	// Range for number of initially infected adults
	private final static int MIN_A_INFECTED = 1;
	private final static int MAX_A_INFECTED = 2;

	// Range for number of initially infected children
	private final static int MIN_C_INFECTED = 1;
	private final static int MAX_C_INFECTED = 3;

	// Range for number of initially infected elders
	private final static int MIN_E_INFECTED = 1;
	private final static int MAX_E_INFECTED = 3;

	// Set speed of the simulation
	private final static float TIME_TRESHOLD = 100000000;

	// ============================== Simulation parameters ==============================//

	private final static Random r = new Random();

	public static void main(String[] args) {

		Grid grid = new Grid(GRID_WIDTH, GRID_HEIGHT);
		generateMap(grid);

		long lastTime = System.nanoTime();
		int time = 0;
		int day = 0;
		int initialEntitiesCount = grid.howManyHuman();
		while (true) {

			long currentTime = System.nanoTime();
			if (currentTime - lastTime > TIME_TRESHOLD) {
				printStat(grid, initialEntitiesCount, day, time);

				System.out.println(grid.toString());
				grid.update(time);
				lastTime = currentTime;
				time++;
				if (time == 241) {
					time = 0;
					day++;
				}
			}

		}
	}

	private static void printStat(Grid grid, int initialEntitiesCount, int day, int time) {
		int currentNumber = grid.howManyHuman();
		int infected = grid.howManyInfected();
		int dead = initialEntitiesCount - currentNumber;
		StringBuilder sb = new StringBuilder();
		sb.append("Day: " + day).append(" Time: " + time).append(" Infected: " + infected).append(" Alive: " + currentNumber).append(" Dead: " + dead);
		System.out.println(sb.toString());
	}

	private static void generateMap(Grid grid) {
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
		addInfectedChildren(houses, grid);
		addInfectedElder(houses, grid);
	}

	private static int rd(int x) {
		return r.nextInt(x + 1);
	}

	private static int rd(int min, int max) {
		return r.nextInt(max - min) + min;
	}

	private static boolean isPosFree(int x, int y, Grid grid) {
		Cell c = grid.getCell(x, y);
		return !c.hasHospital() && !c.hasHouse() && !c.hasWorkPlace();
	}

	private static List<House> generateHouses(Grid grid) {
		int nbrHouse = rd(MIN_HOUSE, MAX_HOUSE);
		List<House> houses = new ArrayList<>();
		for (int i = 0; i < nbrHouse; ++i) {
			int x;
			int y;
			do {
				x = rd(grid.getBorderX() - 1);
				y = rd(grid.getBorderY() - 1);
			} while (!isPosFree(x, y, grid));

			House h = new House(x, y, grid, MAX_C_PER_HOME + MAX_A_PER_HOME + MAX_E_PER_HOME);
			houses.add(h);
			grid.getCell(x, y).addEntity(h);
			grid.addEntity(h);
		}
		return houses;
	}

	private static void generateHospitals(Grid grid) {
		int nbrHospital = rd(MIN_HOSPITAL, MAX_HOSPITAL);
		for (int i = 0; i < nbrHospital; ++i) {
			int x;
			int y;
			do {
				x = rd(grid.getBorderX() - 1);
				y = rd(grid.getBorderY() - 1);
			} while (!isPosFree(x, y, grid));

			Hospital h = new Hospital(x, y, grid, rd(MIN_HOSPITAL_CAPACITY, MAX_HOSPITAL_CAPACITY), rd(MIN_HOSPITAL_POWER, MAX_HOSPITAL_POWER));
			grid.getCell(x, y).addEntity(h);
			grid.addEntity(h);
		}
	}

	private static List<WorkPlace> generateWorkPlaces(Grid grid) {
		int nbrWork = rd(MIN_WORK, MAX_WORK);
		List<WorkPlace> works = new ArrayList<>();
		for (int i = 0; i < nbrWork; ++i) {
			int x;
			int y;
			do {
				x = rd(grid.getBorderX() - 1);
				y = rd(grid.getBorderY() - 1);
			} while (!isPosFree(x, y, grid));

			WorkPlace wp = new WorkPlace(x, y, grid, MAX_HOUSE * MAX_A_PER_HOME);
			works.add(wp);
			grid.getCell(x, y).addEntity(wp);
			grid.addEntity(wp);
		}
		return works;
	}

	private static void addChildren(List<House> houses, Grid grid) {
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

	private static void addElders(List<House> houses, Grid grid) {
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

	private static void addAdults(List<House> houses, List<WorkPlace> workPlaces, Grid grid) {
		for (House h : houses) {
			int x = h.getPosX();
			int y = h.getPosY();
			int num = rd(MAX_A_PER_HOME);
			for (int i = 0; i < num; ++i) {
				WorkPlace wp = workPlaces.get(rd(workPlaces.size() - 1));
				Adult a = new Adult(x, y, grid, false, h, wp);
				grid.getCell(x, y).addEntity(a);
				grid.addEntity(a);
			}
		}
	}

	private static void addInfectedAdults(List<House> houses, List<WorkPlace> workPlaces, Grid grid) {

		int numA = rd(MIN_A_INFECTED, MAX_A_INFECTED);
		for (int i = 0; i < numA; ++i) {
			House h = houses.get(rd(houses.size() - 1));
			int x = h.getPosX();
			int y = h.getPosY();
			WorkPlace wp = workPlaces.get(rd(workPlaces.size() - 1));
			Adult a = new Adult(x, y, grid, true, h, wp);
			grid.getCell(x, y).addEntity(a);
			grid.addEntity(a);
		}

	}

	private static void addInfectedChildren(List<House> houses, Grid grid) {

		int numC = rd(MIN_C_INFECTED, MAX_C_INFECTED);
		for (int i = 0; i < numC; ++i) {
			House h = houses.get(rd(houses.size() - 1));
			int x = h.getPosX();
			int y = h.getPosY();
			Child c = new Child(x, y, grid, true, h);
			grid.getCell(x, y).addEntity(c);
			grid.addEntity(c);
		}

	}

	private static void addInfectedElder(List<House> houses, Grid grid) {

		int numE = rd(MIN_E_INFECTED, MAX_E_INFECTED);
		for (int i = 0; i < numE; ++i) {
			House h = houses.get(rd(houses.size() - 1));
			int x = h.getPosX();
			int y = h.getPosY();
			Elder e = new Elder(x, y, grid, true, h);
			grid.getCell(x, y).addEntity(e);
			grid.addEntity(e);
		}

	}
}
