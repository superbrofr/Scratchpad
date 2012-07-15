
/**
 * A hostile creature found in the game world. Player can attack the monster
 * 
 * @author Charlotte Pierce 
 * @version 1/10.2010
 */
public class Monster
{
    // instance variables - replace the example below with your own
    private int strength; //the strength which the monster can hit with
    private double health;
    private String name;

    /**
     * Constructor for objects of class Monster.
     * @param name The name of the monster.
     * @param strength The strength of the monster.
     * @param health The health of the monster.
     */
    public Monster(String name, int strength, int health)
    {
        this.name = name;
        this.strength = strength;
        this.health = health;
    }

    /**
     * Set the strength of the monster.
     * @param str Strength value to assign.
     */
    public void setStrength(int str)
    {
        strength = str;
    }
    
    /**
     * Set the health of the monster.
     * @param hp Health value to assign.
     */
    public void setHealth(int hp)
    {
        health = hp;
    }
    
    /**
     * Returns a string representing a description of the monster.
     * @return A description of the monster.
     */
    public String getDescription()
    {
        String returnString = "";
        returnString += "\n" + name + " (" + health + "hp) (" + strength + " str)";
        return returnString;
    }
    
    /**
     * Returns the monsters name.
     * @return The monsters' name.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the monsters' strength.
     * @return The monster's strength.
     */
    public double getStrength()
    {
        return strength;
    }
    
    /**
     * Monster gets 'hit' with the specified strength. Health goes down by that strength value.
     * @param strength Strength hit with.
     */
    public void getHit(double strength)
    {
        health -= strength;
    }
    
    /**
     * Returns an integer representing the monster's health.
     * @return The monster's health.
     */
    public double getHealth()
    {
        return health;
    }
}
