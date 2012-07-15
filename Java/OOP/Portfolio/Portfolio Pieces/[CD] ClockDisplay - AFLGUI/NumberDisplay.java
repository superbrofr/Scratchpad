
/**
 * The NumberDisplay class represents a digital number display that can hold
 * values from zero to a given limit. The limit can be specified when creating
 * the display.
 * 
 * @author Charlotte Pierce
 * @version 7/11/2010
 */
public class NumberDisplay
{
    private int limit;
    private int value; //number scored

    /**
     * Constructor for objects of class NumberDisplay.
     * Set the limit at which the display rolls over.
     */
    public NumberDisplay()
    {
        this.limit = 0;
        value = 0;
    }
    
    /**
     * Creates a NumberDisplay which can display any number from 0 to the
     * sepcified limit.
     * @param limit The highest number the display can show.
     */
    public NumberDisplay(int limit)
    {
        this.limit = limit;
        value = 0;
    }

    /**
     * Return the current value of the display.
     * @return The current value of the display.
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Returns the current value of the display in the form of a string.
     * @return The current valeu of the display.
     */
    public String getDisplayValue()
    {
        if(value < 10) {
            return "0" + value;
        }
        else {
            return "" + value;
        }
    }

    /**
     * Sets the value of the display, if that value is between 0 and the
     * limit of the display.
     * @param replacementValue The value to set the display to.
     */
    public void setValue(int replacementValue)
    {
        if((replacementValue >= 0) && (replacementValue < limit)) {
            value = replacementValue;
        }
    }

    /**
     * Increments the display value by 1. If the display reaches the limit,
     * the display rolls over to 0.
     */
    public void increment()
    {
        value = (value + 1) % limit;
    }
}
