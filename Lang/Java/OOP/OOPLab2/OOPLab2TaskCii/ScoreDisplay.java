public class ScoreDisplay
{   
    private NumberDisplay goals;
    private NumberDisplay behinds;
    private int score;
    private String numDisplay;
    
    public ScoreDisplay()
    {
        goals = new NumberDisplay(99);
        behinds = new NumberDisplay(99);
        score = 0;
        updateNumDisplay();
    }
    
    public void scoreGoal()
    {
        goals.increment();
        updateScore();
    }
    
    public void scoreBehind()
    {
        behinds.increment();
        updateScore();
    }
    
    public void updateScore()
    {
        score = (6 * goals.getValue()) + behinds.getValue();
        updateNumDisplay();
    }
    
    public void updateNumDisplay()
    {
        numDisplay = goals.getDisplayValue() + " " + behinds.getDisplayValue() + " " + score;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public String  getNumDisplay()
    {
        return numDisplay;
    }
}
