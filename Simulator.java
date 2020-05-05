import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field containing 
 * rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;    

    private PopulationGenerator popGen;
    
    // Lists of animals in the field.
    private List<Animal> animals;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be >= zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        popGen = new PopulationGenerator(field, animals);
        // Create a view of the state of each location in the field.
        view = new SimulatorView(Simulator.getDefaultDepth(),Simulator.getDefaultWidth());
        popGen.setViewColors(view);
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long 
     * period (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step=1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step. Iterate
     * over the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep()
    {
       step++;

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();        
        // Let all rabbits act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals);
            if(! animal.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);

        view.showStatus(step, field);
    }
        
    public static int getDefaultWidth()
    {
        return DEFAULT_WIDTH;
    }
    
    public static int getDefaultDepth()
    {
        return DEFAULT_DEPTH;
    }
    
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        field.clear();
        popGen.populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    
    
    public void runShortSimulation()
    {
      simulate(100);  
    }
    
    // private boolean checkListFieldEquivalence()
    // {
      // boolean equivalent = true;
      // List<Object> fieldObjects = field.getAllObjects();
      
      // for (Fox fox : foxes)
      // {
         // if(! fieldObjects.contains(fox))
         // {
             // equivalent = false;
            // }
        // }
        
      // for (Rabbit rabbit : rabbits)
      // {
         // if(! fieldObjects.contains(rabbit))
         // {
             // equivalent = false;
            // }
        // }
      
      // for(Object obj : fieldObjects)
      // {
          // if (! (rabbits.contains(obj) || foxes.contains(obj)))
          // {
             // equivalent = false; 
            // }
        // }
        
      // return equivalent;
    // }
    
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
