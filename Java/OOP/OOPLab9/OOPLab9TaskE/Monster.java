
/**
 * A hostile creature found in the game world. Player can attack the monster
 * 
 * @author Charlotte Pierce 
 * @version 1/10.2010
 */
public class Monster extends IntelligentBeing
{
    private String name;

    /**
     * Constructor for objects of class Monster.
     * @param name The name of the monster.
     * @param strength The strength of the monster.
     * @param health The health of the monster.
     */
    public Monster(String name, int strength, double health)
    {
        super(strength, health);
        this.name = name;
    }
    
    /**
     * Returns a string representing a description of the monster.
     * @return A description of the monster.
     */
    public String getDescription()
    {
        String returnString = "";
        returnString += "\n" + name + " (" + getHealth() + "hp) (" + getStrength() + " str)";
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
     * Calculates and returns the strength of the monster's attack.
     * @return The monster's attack strength.
     */
    public double getAttackStr()
    {
        return getStrength();
    }
}
