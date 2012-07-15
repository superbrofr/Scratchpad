/**
 * Simulates a ScoreBoard used for an AFL match. Stores the ScoreDisplay for both the Home and Away teams,
 * and can update and return the values on these displays.
 * 
 * @author Charlotte Pierce
 * @version 7/11/2010
 */
public class ScoreBoard
{
    private ScoreDisplay team1;
    private ScoreDisplay team2;
    private String scoreBoard;
    
    /**
     * Instantiates the two ScoreDisplays, one for each team.
     */
    public ScoreBoard()
    {
        team1 = new ScoreDisplay();
        team2 = new ScoreDisplay();
    }
    
    /**
     * Returns the scores of both teams, showing which side of the display is for the home team, and which for the
     * away. Adds an asterisks next to the score of the team which is currently winning.
     * @return The scores of both teams.
     */
    public String getScores()
    {
        scoreBoard = "[HOME]   ";
        if(team1.getScore() > team2.getScore()){
            scoreBoard += team1.getNumDisplay() + "*  :  " + team2.getNumDisplay();
        }
        else if(team1.getScore() < team2.getScore()){
            scoreBoard += team1.getNumDisplay() + "  :  " + team2.getNumDisplay();
        }
        else{
            scoreBoard += team1.getNumDisplay() + "  :  " + team2.getNumDisplay();
        }
        scoreBoard += "   [AWAY]";
        return scoreBoard;
    }
    
    /**
     * Simulates the scoring of a goal by the home team.
     */
    public void team1Goal()
    {
        team1.scoreGoal();
    }
    
    /**
     * Simulates the scoring of a goal by the away team.
     */
    public void team2Goal()
    {
        team2.scoreGoal();
    }
    
    /**
     * Simulates the scoring of a behind by the home team.
     */
    public void team1Behind()
    {
        team1.scoreBehind();
    }
    
    /**
     * Simulates the scoring of a behind by the away team.
     */    
    public  void team2Behind()
    {
        team2.scoreBehind();
    }
}