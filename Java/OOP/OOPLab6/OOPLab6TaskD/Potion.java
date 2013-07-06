
/**
 * Write a description of class Potion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
