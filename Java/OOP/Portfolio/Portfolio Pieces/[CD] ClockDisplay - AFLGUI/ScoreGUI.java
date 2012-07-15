import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;

/**
 * The GUI and main controls for the scoreboard system. This class controls the interface
 * for the entire scoreboard system. To run, just run the main method of this class.
 * 
 * @author Charlotte Pierce
 * @version 7/11/2010
 */
public class ScoreGUI
{
    private JFrame frame; // the interaction screen
    private JLabel scoresText;
    private ScoreBoard scoreBoard;
    
    /**
     * Instantiates the fields - creates the ScoreBoard, the frame on which to show GUI controls,
     * and sets the initial value of scoresText.
     */
    public ScoreGUI()
    {
        scoreBoard = new ScoreBoard();
        frame = new JFrame("AFL Score Board"); // creates the main window
        scoresText = new JLabel(scoreBoard.getScores()); // creates label to show the teams' scores
    }
    
    /**
     * Creates an instance of the GUI and sets up all controls.
     */
    public static void main(String[] args)
    {
        ScoreGUI mainApp = new ScoreGUI();
        mainApp.beginApp();
    }
    
    /**
     * Sets up all GUI controls and event listeners.
     * Packs the frame and makes it visible.
     */
    private void beginApp()
    {
        Container contentPane = frame.getContentPane(); // gets the content pane so can add stuff to it
        contentPane.setLayout(new BorderLayout()); // set the main window to have a BorderLayout
        
        // set and add the scoresText to the contentPane
        scoresText.setHorizontalAlignment(JLabel.CENTER);
        scoresText.setFont(new Font("Calibri", Font.PLAIN, 20));
        contentPane.add(scoresText);
        
        JPanel scoreControls = new JPanel(new GridLayout(1, 4)); // creates a 1 x 4 grid to store the buttons on
        // create buttons for scoring goals and behinds
        JButton goal1 = new JButton("Home Goal");
        JButton behind1 = new JButton("Home Behind");
        JButton goal2 = new JButton("Away Goal");
        JButton behind2 = new JButton("Away Behind");
        // add action listeners to the buttons
        goal1.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        homeGoal();
                                    }
                                });
        behind1.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        homeBehind();
                                    }
                                });
        goal2.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        awayGoal();
                                    }
                                });
        behind2.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        awayBehind();
                                    }
                                });
        // add the buttons to the grid
        scoreControls.add(goal1);
        scoreControls.add(behind1);
        scoreControls.add(goal2);
        scoreControls.add(behind2);
        //add the grid to the main window
        contentPane.add(scoreControls, BorderLayout.SOUTH);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Simulates a goal for the home team.
     */
    private void homeGoal()
    {
        scoreBoard.team1Goal();
        updateDisplay();
    }

    /**
     * Simulates a behind for the home team.
     */
    private void homeBehind()
    {
        scoreBoard.team1Behind();
        updateDisplay();
    }
    
    /**
     * Simulates a goal for the away team.
     */
    private void awayGoal()
    {
        scoreBoard.team2Goal();
        updateDisplay();
    }

    /**
     * Simulates a behind for the away team.
     */
    private void awayBehind()
    {
        scoreBoard.team2Behind();
        updateDisplay();
    }
    
    /**
     * Updates the JLabel showing the scores to show the most recent score data.
     * Called after every goal/behind.
     */
    private void updateDisplay()
    {
        scoresText.setText(scoreBoard.getScores());
    }
}
