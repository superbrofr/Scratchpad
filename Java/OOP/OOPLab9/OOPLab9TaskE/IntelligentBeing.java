
/**
 * A class representing an object within the game that can be perceived as being 'alive'.
 * This would most likely be a human, a monster/enemy (even monsters which within the context of the
 * game are of a robotic nature), or the player character.
 * Every being has strength and health. Strength affects their damage in battle.
 * 
 * @author Charlotte Pierce
 * @version 5/11/2010
 */
public abstract class IntelligentBeing
{
    private int strength; //the strength which the being can hit with
    private double health;
    
    protected IntelligentBeing(int strength, double health)
    {
        this.strength = strength;
        this.health = health;
    }
    
    /**
     * Returns the being's strength.
     * @return The being's strength.
     */
    protected int getStrength()
    {
        return strength;
    }
    
    /**
     * Returns the being's current health.
     * @return The being's current health.
     */
    protected double getHealth()
    {
        return health;
    }
    
    /**
     * Set the health of the being.
     * @param hp Health value to assign.
     */
    protected void setHealth(int hp)
    {
        this.health = hp;
    }
    
    /**
     * Being gets 'hit' with the specified strength. Health goes down by that strength value.
     * @param strength Strength hit with.
     */
    public void getHit(double howMuch)
    {
        health -= howMuch;
    }
    
    /**
     * Adds a certain amount of health to the being's current health.
     * @param howMuch The amount of health points to add.
     */
    public void addHealth(double howMuch)
    {
        health += howMuch;
    }
    
    /**
     * Calculates and returns the strength of the being's attack.
     * @return The being's attack strength.
     */
    public abstract double getAttackStr();
}
