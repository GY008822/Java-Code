import java.util.*;
/**
 * Write a description of class Animal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Animal
{
   protected boolean alive;
   protected Location location;
   protected Field field;
   
   public Animal(Location location,Field field)
   {
       alive = true;
       this.location = location;
       this.field = field;
    }
    
   public Field getField()
   {
      return field; 
    }
    
   protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
   public boolean isAlive()
    {
        return alive;
    }
    
   protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
}
