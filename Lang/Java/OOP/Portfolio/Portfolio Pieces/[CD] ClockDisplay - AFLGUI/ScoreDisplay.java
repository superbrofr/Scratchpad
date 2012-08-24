/**
 * The ScoreDisplay uses two instances of the NumberDisplay class to create a scoreboard for
 * an AFL team.
 * 
 * @author Charlotte Pierce
 * @version 7/11/2010
 */

public class ScoreDisplay
{   
    private NumberDisplay goals;
    private NumberDisplay behinds;
    private int score;
    private String numDisplay;
    
    /**
     * Creates a new ScoreDisplay. Instantiates two objects of type NumberDisplay, one for the
     * goals and one for the behinds. Sets the initial score to 0.
     */
    public ScoreDisplay()
    {
        goals = new NumberDisplay(99);
        behinds = new NumberDisplay(99);
        score = 0;
        updateNumDisplay();
    }
    
    /**
     * Simulates the scoring of a goal.
     */
    public void scoreGoal()
    {
        goals.increment();
        updateScore();
    }
        
    /**
     * Simulates the scoring of a behind.
     */
    public void scoreBehind()
    {
        behinds.increment();
        updateScore();
    }
    
    /**
     * Updates the value of the total score, according to how many goals and how many behinds
     * have been scored.
     */
    public void updateScore()
    {
        score = (6 * goals.getValue()) + behinds.getValue();
        updateNumDisplay();
    }
    
    /**
     * Updates the string representing the score of the team.
     */
    public void updateNumDisplay()
    {
        numDisplay = goals.getDisplayValue() + "    " + behinds.getDisplayValue() + "    " + score;
    }
    
    /**
     * Returns the total score of the team.
     * @return The totals score of the team.
     */
    public int getScore()
    {
        return score;
    }
    
    /**
     * Returns a summary of the teams performance in the form of a String.
     * Shown in the form "<number of goals> <number of behinds> <total score>"
     * @return The team's score.
     */
    public String getNumDisplay()
    {
        try
        {
            return numDisplay;
        }
        catch(NullPointerException npe)
        {
            return "00 00 0";
        }
    }
}
