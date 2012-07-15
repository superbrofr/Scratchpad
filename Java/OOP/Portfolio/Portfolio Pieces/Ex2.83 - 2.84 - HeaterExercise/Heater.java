/**
 * A heater. Holds a certain temperature, which must be within
 * a range of temperatures defined by the minimum and maximum
 * fields. 
 * Heater can be cooled/warmed by the amount held in increment.
 * 
 * @author Charlotte Pierce
 * @version 14/8/2010
 */

public class Heater
{
    private int temperature;
    private int min; //minimum temperature
    private int max; //maximum temperature
    private int increment; //amount to change temperature by
    
    /**
     * Instantiates the heater with minimum and maximum temperatures.
     * @param minSet The minimum temperature for the heater.
     * @param maxSet The maximum temperature for the heater.
     */
    public Heater(int minSet, int maxSet)
    {
        temperature = 15;
        min = minSet;
        max = maxSet;
        increment = 5;
    }
    
    /**
     * Increases the temperature of the heater by the value in
     * increment, unless that modification would take the
     * temperature above the maximum value allowed.
     */
    public void warmer()
    {
        if((temperature + increment) > max){
            //do nothing...
        }
        else
        {
            temperature = temperature + increment;
        }
    }
    
    /**
     * Decreases the temperature of the heater by the value in
     * increment, unless that modification would take the
     * temperature below the minimum value allowed.
     */
    public void cooler()
    {
        if((temperature - increment) < min){
            //do nothing...
        }
        else{
            temperature = temperature - increment;
        }
    }
    
    /**
     * Used to change the value by which the temperature of the
     * heater is incremented.
     * @param inc The value to change increment to.
     */
    public void setIncrement(int inc)
    {
        if(inc < 0){
            System.out.println("Invalid value to increment.");
        }
        else{
            increment = inc;
        }
    }
    
    /**
     * Returns the temperature of the heater.
     * @return The temperature of the heater.
     */
    public int getTemp()
    {
        return temperature;
    }
}
