
/**
 * An item that can be interacted with in the game.
 * 
 * @author Charlotte Pierce
 * @version 1/10/2010
 */
public class Item
{
    private String name;
    private int weight;
    private int value;
    private int damage;

    /**
     * Constructor for objects of class Item
     * @param aName Name of the item.
     * @param aWeight Weight of the item.
     * @param aValue Value of the item.
     * @param aDamage Damage of the item.
     */
    public Item(String aName, int aWeight, int aValue, int aDamage)
    {
        name = aName;
        weight = aWeight;
        value = aValue;
        damage = aDamage;
    }

    /**
     * Returns a string representing the name of the item.
     * @return The name of the item.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns an int representing the weight of the item.
     * @return The weight of the item.
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Returns an int representing the value of the item.
     * @return The value of the item.
     */
    public int getValue()
    {
        return value;
    }
    
    /**
     * Returns an int representing the damage of the item.
     * @return The damage of the item.
     */
    public int getDamage()
    {
        return damage;
    }
    
    /**
     * Returns a String description of the item.
     * @return Description of the item.
     */
    public String getDescription()
    {
        String returnString = "";
        returnString += "\n " + name + "(" + weight + " weight) (" + value + " value) (" + damage + " dmg)";
        return returnString;
    }
}
