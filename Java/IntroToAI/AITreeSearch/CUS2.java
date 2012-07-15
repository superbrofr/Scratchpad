/**
* A puzzle solver using the a custom (CUS2) algorithm.
* The algorithm is informed, thus uses an evaluation function, which is a combination of the heuristic
* and path costs.
* The heuristic is calculated by the number of numbers in the puzzle grid that are not in their goal positions.
* Also takes into account the path cost (i.e. path cost increases by 1 for every level of depth in the
* search tree).
* @author Charlotte Pierce
*/
import java.util.Collections;

public class CUS2 extends GenericSolver
{
  /**
  * Creates a custom search solver.
  * @param startState Starting state for the puzzle - required to make the starting node.
  * @param goalState The goal state for the puzzle.
  * @param aMethod The solving method - only used in printSolution()
  * @param aFileName The file containing the puzzle set-up data - only used in printSolution()
  */
  public CUS2(int[][] startState, int[][] goalState, String aMethod, String aFileName)
  {
    super(startState, goalState, aMethod, aFileName);
  }
  
  /**
  * Runs the solver.
  * Checks that the start node isn't already at the goal state.
  * If not, removes the first node from the fringe, and checks if it is the goal node. If it is not, 
  * it expands the node, adds it to the explored set, and sorts the fringe (sorting puts the node with the
  * lowest total cost - i.e. the lowest value of f(n) at the start).
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
  * or explored set with the same state, it updates the heuristic of the node (for sorting purposes) a adds the node to the end of the fringe.
  * @param parentNode The parent being expanded by this move.
  * @param moveState The parent's state.
  * @param anAction The action to be performed on the parent to create the new node.
  */
  protected void makeMove(Node parentNode, int[][] moveState, Action anAction)
  {
    Node newNode = new Node(parentNode, moveState, anAction);
    //check both fringe and explored set to avoid loops - doesn't check for goal until going to expand the node
    if(!inFringe(newNode) && !inExploredSet(newNode)){
      updateTotalCost(newNode);
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
  * Updates the total cost (i.e. the evaluation function f(n)) for the node.
  * Updates both the path and heuristic costs for the node.
  * @param aNode The node to update.
  */
  private void updateTotalCost(Node aNode)
  {
    updatePathCost(aNode);
    updateHeuristic(aNode);
  }

  /**
  * Updates the path cost of the node with the depth (move cost is 1 thus path cost = depth)
  * @param aNode The node to update.
  */
  private void updatePathCost(Node aNode)
  {
    int pathVal = 0;
    Node nextNode = aNode;
    do{
      pathVal++;
      nextNode = nextNode.getParent();
    }while(nextNode.getParent() != null);
    aNode.setPathCost(pathVal);
  }

  /**
  * Updates the heuristic value for the node. Uses the number of tiles in the puzzle grid not
  * currently in their goal position.
  * @param aNode The node to update.
  */
  private void updateHeuristic(Node aNode)
  {
    int updateVal = 0;
    for(int i = 0; i < width; i++){
      for(int j = 0; j < height; j++){
        if((aNode.getState()[i][j] != goalState[i][j]) && (aNode.getState()[i][j] != 0)){
          updateVal++;
        }
      }
    }
    aNode.setHeuristicCost(updateVal);
  }
}