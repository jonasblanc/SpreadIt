# SpreadIt
SpreadIt is a basic simulation of the spreading of a virus in a population. This project was done in 48h during [Lauzhack covid-19](https://covid19.lauzhack.com).

This repository contains [java version](src) which prints the simulation in the console and a [processing version](Processing). Please be indulgent with the processing one, we did not spend much time on it.

## Simulation
The simulation takes place in a grid with houses, workplaces and hospitals. The population consists of children, adutls and elders. Each category has a different behaviour: playing around, going to work or enjoying a hot sunny day sitting on park bench. They share some common activities such as going at home at night or going to hospital if they are infected. Humans harun the risk of becoming infected every time they encounter an infected person. If they stay infected for too long, they die. To avoid a painfull death they must be treated in hospital, but be aware that hospitals have maximum capacity.

## Java version
All simulation parameters are in [Main](src/Main.java) which is also used to run the simulation.
### Console representation
The grid is delimited by "*".

Upper case for healty people / lower case for infected poeple:
  * "E" for elders
  * "A" for adults
  * "C" for children
  
Buildings are surrounded by "()" if there are several entites inside:
  * "^" for houses
  * "+" for hospitals
  * "W" for workplaces

## Processing version
All simulation parameters are in [Simulation](Processing/Simulation/Simulation.pde) which is also used to run the simulation.
### Processing representation

Infected poeple have a yellow halo. The size of the entities scale if several entites are in the same cell.
  * Purple circle for elders
  * Red circle adults
  * Blue circle for children
  
Buildings are surrounded by "()" if there are several entites inside:
  * Orange square for houses
  * Green square for hospitals
  * Grey square for workplaces
