
/**
 * Simulates a clock. Has an hour and minutes display, and can be set to tick a single minute.
 * The overall time can also be set.
 * 
 * @author Charlotte Pierce
 * @version 3/11/2010
 */
public class ClockDisplay
{
    private LinkedDisplay clock;
    
    /**
     * Creates a new linked display to store the time.
     */
    public ClockDisplay()
    {
        clock = new LinkedDisplay();
        showTime();
    }
    
    /**
     * Prints out the current time stored in the clock display.
     */
    private void showTime()
    {
        System.out.println(clock.getClockValue());
    }
    
    /**
     * Ticks over one minute
     */
    public void tick()
    {
        clock.tick();
        showTime();
    }
    
    /**
     * Change the value of the hours.
     */
    public void setHours(int toWhat)
    {
        clock.setHours(toWhat);
        showTime();
    }
    
    /**
     * Change the value of the minutes.
     */
    public void setMinutes(int toWhat)
    {
        clock.setMinutes(toWhat);
        showTime();
    }
}
