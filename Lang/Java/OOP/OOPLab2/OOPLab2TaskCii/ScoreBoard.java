public class ScoreBoard
{
    private ScoreDisplay team1;
    private ScoreDisplay team2;
    private String scoreBoard;
    
    public ScoreBoard()
    {
        team1 = new ScoreDisplay();
        team2 = new ScoreDisplay();
    }
    
    public void showScore()
    {
        if(team1.getScore() > team2.getScore()){
            scoreBoard = team1.getNumDisplay() + "* : " + team2.getNumDisplay();
        }
        else if(team1.getScore() < team2.getScore()){
            scoreBoard = team1.getNumDisplay() + " : " + team2.getNumDisplay() + "*";
        }
        else{
            scoreBoard = team1.getNumDisplay() + " : " + team2.getNumDisplay();
        }
        System.out.println(scoreBoard);
    }
    
    public void team1Goal()
    {
        team1.scoreGoal();
    }
    
    public void team2Goal()
    {
        team2.scoreGoal();
    }
    
    public void team1Behind()
    {
        team1.scoreBehind();
    }
    
    public  void team2Behind()
    {
        team2.scoreBehind();
    }
}