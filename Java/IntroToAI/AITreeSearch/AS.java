import java.util.Collections;

/**
* A puzzle solver using the A* (AS) algorithm.
* @author Divyesh Prakash and Charlotte Pierce
*/
public class AS extends GenericSolver
{
  /**
  * Creates a search solver using the A* method and the ManHattan Distance heuristic.
  * @param startState Starting state for the puzzle - required to make the starting node.
  * @param goalState The goal state for the puzzle.
  * @param aMethod The solving method - only used in printSolution()
  * @param aFileName The file containing the puzzle set-up data - only used in printSolution()
  */
  public AS(int[][] startState, int[][] goalState, String aMethod, String aFileName)
  {
    super(startState, goalState, aMethod, aFileName);
  }
  
  /**
  * Checks if the start node is already at the goal state.
  * If not, removes the first node from the fringe, and checks if it is the goal node. If it is not, 
  * it expands the node, adds it to the explored set, and sorts the fringe (sorting puts the node with the
  * lowest total cost.
  * the lowest total cost when using A* is f(n). which is g(n) (the cost to each the goal) + h(n) (the cost
  * to get from the node to the goal)
  */
  public void run()
  {
	if(isGoal(startNode)){
      System.out.println(fileName + " " + method + " " + nodesFound);
      System.exit(1);
    }
    else{
      updateHeuristic(startNode);
      fringe.add(startNode);
    }

    while(fringe.size() > 0){
      if(isGoal(fringe.get(0))){
        printSolution(fringe.get(0));
      }
      else{
        expandNode(fringe.get(0));
        exploredSet.add(fringe.get(0));
        fringe.remove(0);
        sortFringe();
      }
    }
    System.out.println(fileName + " " + method + " " + nodesFound + " No solution found.");
    System.exit(1);
  }
  
  /**
  * Makes a move. Assumes that the move being made is valid.
  * Creates a new node, and checks whether it contains the goal state. If it is not, and there isn't already a node in the fringe
  * or explored set with the same state, it updates the heuristic of the node (for sorting purposes) and adds the node to the end of the fringe.
  * @param parentNode The parent being expanded by this move.
  * @param moveState The parent's state.
  * @param anAction The action to be performed on the parent to create the new node.
  */
  protected void makeMove(Node parentNode, int[][] moveState, Action anAction)
  {
    Node newNode = new Node(parentNode, moveState, anAction);
    //check both fringe and explored set to avoid loops - doesn't check for goal until going to expand the node
    if(!inFringe(newNode) && !inExploredSet(newNode)){
      updateHeuristic(newNode);
      fringe.add(newNode);
    }
  }
  
  /**
  * Sorts the fringe according to heuristic - in this case lowest evaluation function to highest evaluation function 
  */
  private void sortFringe()
  {
    Collections.sort(fringe);
  }
  
  /**
  * Updates the heuristic value for the node. Uses the sum of manhanttan distances for each non-blank tile not in its goal space.
  * @param aNode The node to update.
  */
  private void updateHeuristic(Node aNode)
  {
    int updateVal = 0;
    for(int i = 0; i < width; i++){
      for(int j = 0; j < height; j++){
        if((aNode.getState()[i][j] != goalState[i][j]) && (aNode.getState()[i][j] != 0)){
          updateVal += getManhattanDistance(i, j, aNode.getState()[i][j]);
        }
      }
    }
    aNode.setHeuristicCost(updateVal);
	aNode.setPathCost((aNode.getPathCost() + 1));
  }
  
  /**
  * Gets the manhattan distance for a number in the puzzle grid, given its current position.
  * @param xPos The current x-co-ordinate of the number in the puzzle grid.
  * @param yPos The current y-co-ordinate of the number in the puzzle grid.
  * @param checkNumber The value of the tile in the puzzle grid.
  * @return The manhattan distance for the number, given its current position in the puzzle grid.
  */
  private int getManhattanDistance(int xPos, int yPos, int checkNumber)
  {
    int returnX = 0;
    int returnY = 0;
    for(int i = 0; i < width; i++){
      for(int j = 0; j < height; j++){
        if(checkNumber == goalState[i][j]){
          if(xPos > i)
            returnX = (xPos - i);
          else
            returnX = (i - xPos);
          if(yPos > j)
            returnY = (yPos - j);
          else
            returnY = (j - yPos);
        }
      }
    }
    return (returnX + returnY);
  }
}
