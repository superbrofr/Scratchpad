
/**
 * The NumberDisplay class represents a digital number display that can hold
 * values from zero to a given limit. The limit can be specified when creating
 * the display. The values range from zero (inclusive) to limit-1. If used,
 * for example, for the seconds on a digital clock, the limit would be 60, 
 * resulting in display values from 0 to 59. When incremented, the display 
 * automatically rolls over to zero when reaching the limit.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
public class NumberDisplay
{
    private int limit; // max value to store in the object
    private int value; //number scored - stored as Integer can be converted to String, and length gotten

    /**
     * Constructor for objects of class NumberDisplay.
     * Set the limit at which the display rolls over.
     */
    public NumberDisplay()
    {
        this.limit = 0;
        value = 0;
    }
    
    public NumberDisplay(int limit)
    {
        this.limit = limit;
        value = 0;
    }
    
    /**
     * Returns the value of the display
     */
    public int getValue()
    {
        return value;
    }

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
     * Sets the value of the number display. Checks that the new number
     * is no longer than the maximum number of digits allowed.
     * @param replacementValue The value to change to.
     */
    public void setValue(Integer replacementValue)
    {
        if((replacementValue >= 0) && (replacementValue < limit)) {
            value = replacementValue;
        }
    }

    /**
     * Adds 1 to the value of the display. If this causes the display to
     * be over the maximum number of digits, the display is reset to 1.
     * @return True if the display reset to 1, else false.
     */
    public boolean increment()
    {
        value = (value + 1) % limit;
        if(value == 0){
            return true;
        }
        else{
            return false;
        }
    }
}
