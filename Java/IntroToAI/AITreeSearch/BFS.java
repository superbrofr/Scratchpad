import java.util.ArrayList;

/**
* A puzzle solver using the breadth-first search (BFS) algorithm.
* @author Charlotte Pierce
*/
public class BFS extends GenericSolver
{
  /**
  * Creates a breadth-first search solver.
  * @param startState Starting state for the puzzle - required to make the starting node.
  * @param goalState The goal state for the puzzle.
  * @param aMethod The solving method - only used in printSolution()
  * @param aFileName The file containing the puzzle set-up data - only used in printSolution()
  */
  public BFS(int[][] startState, int[][] goalState, String aMethod, String aFileName)
  {
    super(startState, goalState, aMethod, aFileName);
  }

  /**
  * Runs the solver.
  * Checks that the start node isn't already at the goal state.
  * If not, expands the first node in the fringe, adds it to the explored set, then removes the 
  * node from the fringe until the goal node is found.
  */
  public void run()
  {
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
      expandNode(fringe.get(0));
      exploredSet.add(fringe.get(0));
      fringe.remove(0);
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