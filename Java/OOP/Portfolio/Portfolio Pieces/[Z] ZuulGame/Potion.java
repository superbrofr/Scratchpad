
/**
 * A potion. Restores the drinker's health by 50 points.
 * 
 * @author Charlotte Pierce
 * @version 6/11/2010
 */
public class Potion extends Item
{
    private int restoreVal;

    /**
     * Constructor for objects of class Potion
     */
    public Potion()
    {
        super("potion", 25, 500, 0);
        restoreVal = 50;
    }
    
    public int getRestoreVal()
    {
        return restoreVal;
    }
}
