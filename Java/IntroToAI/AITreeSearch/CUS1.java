import java.util.Random;
/**
* A puzzle solver using an uninformed custom (CUS1) algorithm.
* @author Divyesh Prakash
*/
public class CUS1 extends GenericSolver
{
  /**
  * Creates a solver for a Custom Search Search.
  * The solver selects a random node from the fringe until it finds the goal state
  * @param startState Starting state for the puzzle - required to make the starting node.
  * @param goalState The goal state for the puzzle.
  * @param aMethod The solving method - only used in printSolution()
  * @param aFileName The file containing the puzzle set-up data - only used in printSolution()
  */
  public CUS1(int[][] startState, int[][] goalState, String aMethod, String aFileName)
  {
    super(startState, goalState, aMethod, aFileName);
  }
  
  public void run()
  {
	Random rng = new Random();
	int rnd = 0 ;
	
	//Check start node isn't already goal state
    if(isGoal(startNode)){
      System.out.println(fileName + " " + method + " " + nodesFound);
      System.exit(1);
    }
    else{
      fringe.add(startNode);
    }

    //Go through the fringe
    while(fringe.size() > 0){
	  rnd = rng.nextInt(fringe.size());
      expandNode(fringe.get(rnd));
      exploredSet.add(fringe.get(rnd));
      fringe.remove(rnd);
    }
    //No solution found.
    System.out.println(fileName + " " + method + " " + nodesFound + " No solution found.");
    System.exit(1);
  }
  
 /**
  * Makes a move. Assumes that the move being made is valid.
  * Creates a new node, and checks whether it contains the goal state. If it is not, it adds the node to
  * the end of the fringe if there is not already a node in the fringe or explored set with the same state.
  * @param parentNode The parent being expanded by this move.
  * @param moveState The parent's state.
  * @param anAction The action to be performed on the parent to create the new node.
  */
  protected void makeMove(Node parentNode, int[][] moveState, Action anAction)
  {
    Node newNode = new Node(parentNode, moveState, anAction);
    if(isGoal(newNode))
      printSolution(newNode);
    else if(!inFringe(newNode) && !inExploredSet(newNode))
      fringe.add(newNode);
  }
}