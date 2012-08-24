import java.io.*;
import java.util.StringTokenizer;

/**
* Front-end to the NxM puzzle solver.
* Call this class to run the solver.
* Call in the following form: NMPuzzleSolver <inputFile.txt> <searchMethod>
* @author Charlotte and Div
*/
public class NMPuzzleSolver
{
  private static String solveMethod;
  private static String fileName;
  private int[][] startState;
  private int[][] goalState;
  
  /**
  * Constructor for solver.
  * Sets the start states and goal states.
  */
  public NMPuzzleSolver()
  {
    initialiseState();
  }
  
  /**
  * Main method, arguments need to be in the form: "<inputFile.txt> <searchMethod>"
  */
  public static void main(String args[])
  {
    if(args.length != 2){
      System.out.println("Invalid number of arguments.");
      System.exit(0);
    }
    solveMethod = args[1];
    fileName = args[0];
    NMPuzzleSolver solver = new NMPuzzleSolver();
    solver.run();
  }
  
  /**
  * Reads input file, creates puzzle grid and stores startState and goalState.
  */
  private void initialiseState()
  {
    try
    {
      File file = new File(fileName);
      BufferedReader reader = new BufferedReader(new FileReader(file));
      int height = Integer.parseInt(reader.readLine());
      int width = Integer.parseInt(reader.readLine());
      startState = getState(reader.readLine(), width, height);
      goalState = getState(reader.readLine(), width, height);
    }
    catch(Exception e)
    {
      System.out.println("File read error.");
    }
  }
  
  /**
  * Converts a line of text into a state grid.
  * @param line The line of text to convert - needs to be in the form "<number>@(<xCo-ord>, <yCo-ord>)  <number>@(<xCo-ord>, <yCo-ord>)"
  * @param width Width of the state grid
  * @param height Height of the state grid
  * @return The created state grid.
  */
  private int[][] getState(String line, int width, int height)
  {
    int[][] returnState = new int[width][height];
    StringTokenizer tokenizer = new StringTokenizer(line, "  ");
    
    while(tokenizer.hasMoreTokens()){
      String token = tokenizer.nextToken();
      String[] atBracketSplit = token.split("@\\(");
      int number = Integer.parseInt(atBracketSplit[0]);
      String[] commaSplit = atBracketSplit[1].split(",");
      int x = Integer.parseInt(commaSplit[0]) - 1;
      int y = Integer.parseInt(commaSplit[1].replace(")", "")) - 1;
      returnState[x][y] = number;
    }
    
    return returnState;
  }
  
  /**
  * Checks the search method being used, creates a solver of that type and runs it.
  */
  private void run()
  {
    solveMethod = solveMethod.toUpperCase();
    if(solveMethod.equals("BFS")){
      BFS solver = new BFS(startState, goalState, solveMethod, fileName);
      solver.run();
    }
    else if(solveMethod.equals("DFS")){
      DFS solver = new DFS(startState, goalState, solveMethod, fileName);
      solver.run();
    }
    else if(solveMethod.equals("GBFS")){
      GBFS solver = new GBFS(startState, goalState, solveMethod, fileName);
      solver.run();
    }
    else if(solveMethod.equals("AS")){
      AS solver = new AS(startState, goalState, solveMethod, fileName);
      solver.run();
    }
    else if(solveMethod.equals("CUS1")){
      CUS1 solver = new CUS1(startState, goalState, solveMethod, fileName);
      solver.run();
    }
    else if(solveMethod.equals("CUS2")){
      CUS2 solver = new CUS2(startState, goalState, solveMethod, fileName);
      solver.run();
    }
  }
}