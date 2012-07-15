import java.util.ArrayList;

/**
* A generic solver class. Implements all methods required by any form of solver.
* Needs to be extended to the specific solver (i.e. extended to use a heuristic).
* @author Charlotte
*/
public abstract class GenericSolver
{
  protected int nodesFound; //number of nodes in the tree
  protected Node startNode;
  protected int[][] goalState;
  protected ArrayList<Node> fringe;
  protected ArrayList<Node> exploredSet;
  protected int width, height; //width and height of puzzle grid - saves calculating every time a move is made
  protected String fileName;
  protected String method;

  /**
  * Creates a generic solver.
  * @param startState Starting state for the puzzle - required to make the starting node.
  * @param goalState The goal state for the puzzle.
  * @param aMethod The solving method - only used in printSolution()
  * @param aFileName The file containing the puzzle set-up data - only used in printSolution()
  */
  public GenericSolver(int[][] startState, int[][] goalState, String aMethod, String aFileName)
  {
    method = aMethod;
    fileName = aFileName;
    nodesFound = 1; //always going to be at least 1 - the start node
    startNode = new Node(null, startState, null);
    this.goalState = goalState;
    fringe = new ArrayList<Node>();
    exploredSet = new ArrayList<Node>();
    width = goalState.length;
    height = goalState[0].length;
  }

  /**
  * The main algorithm for the solver. This is the only method called by the main puzzle solver class.
  * Needs to define the order and method of going through the fringe, sorting the fringe and adding/removing
  * nodes to/from the explored set.
  */
  public abstract void run();

  /**
  * Expands the specified node with all valid moves for each blank cell.
  * Adds the new nodes to the end of the fringe (fringe needs to be sorted manually).
  * @param aNode The node to expand.
  */
  protected void expandNode(Node aNode){
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        if(aNode.getState()[j][i] == 0){
          int[][] nodeState = new int[width][height];
          copyState(nodeState, aNode.getState());
          //Process each move for the empty cell
          if(canMove((j + 1), (i + 1), Direction.LEFT)){
            nodesFound++;
            makeMove(aNode, nodeState, new Action((j + 1), (i + 1), Direction.LEFT));
          }
          if(canMove((j + 1), (i + 1), Direction.RIGHT)){
            nodesFound++;
            makeMove(aNode, nodeState, new Action((j + 1), (i + 1), Direction.RIGHT));
          }
          if(canMove((j + 1), (i + 1), Direction.UP)){
            nodesFound++;
            makeMove(aNode, nodeState, new Action((j + 1), (i + 1), Direction.UP));
          }
          if(canMove((j + 1), (i + 1), Direction.DOWN)){
            nodesFound++;
            makeMove(aNode, nodeState, new Action((j + 1), (i + 1), Direction.DOWN));
          }
        }
      }
    }
  }

  /**
  * Calculates whether the specified grid cell can be moved in the specified direction.
  * @param xPos The x-co-ordinate (not index) of the grid cell.
  * @param yPos The y-co-ordinate (not index) of the grid cell.
  * @param aDirection The direction in which to move the grid cell.
  * @return Whether the move is valid or not.
  */
  private boolean canMove(int xPos, int yPos, Direction aDirection){
    return (validMove(new Action((xPos), (yPos), aDirection)));
  }

  /**
  * Makes the move.
  * Needs to create a new node, and then add it to the fringe depending on relevant checks.
  * (i.e. some methods don't care about the explored set)
  * Assumes the move has already been found to be valid.
  * @param parentNode The parent being expanded by this move.
  * @param moveState The parent's state.
  * @param anAction The action to be performed on the parent to create the new node.
  */
  protected abstract void makeMove(Node parentNode, int[][] moveState, Action anAction);

  /**
  * Checks if the suggested action is a valid move (i.e. won't push a tile off the edge of the puzzle grid)
  * @param anAction The action to check.
  * @return True if the action is valid, otherwise false.
  */
  private boolean validMove(Action anAction)
  {
    if(anAction.getDirection() == Direction.LEFT){
      if((anAction.getX() - 1) == 0)
        return false;
      else
        return true;
    }
    else if(anAction.getDirection() == Direction.RIGHT){
      if((anAction.getX() + 1) > width)
        return false;
      else
        return true;
    }
    else if(anAction.getDirection() == Direction.UP){
      if((anAction.getY() - 1) == 0)
        return false;
      else
        return true;
    }
    else if(anAction.getDirection() == Direction.DOWN){
      if((anAction.getY() + 1) > height)
        return false;
      else
        return true;
    }
    return false;
  }

  /**
  * Copies the structure of one state into another.
  * Needs to be used when creating a new node to avoid referencing issues.
  * @param copyTo The puzzle grid to copy the state to.
  * @param copyFrom The puzzle grid to copy from.
  */
  private void copyState(int[][] copyTo, int[][] copyFrom)
  {
    for(int i = 0; i < width; i++){
      for(int j = 0; j < height; j++){
        copyTo[i][j] = copyFrom[i][j];
      }
    }
  }

  /**
  * Checks if a node contains the goal state.
  * @param aNode The node to check.
  * @return True if aNode contains the goal state, otherwise false.
  */
  protected boolean isGoal(Node aNode)
  {
    for(int i = 0; i < width; i++){
      for(int j = 0; j < height; j++){
        if(aNode.getState()[i][j] != goalState[i][j]){
          return false;
        }
      }
    }
    return true;
  }

  /**
  * Checks if two nodes have the same state - used for checking for repeat nodes.
  * @param aNode One node to compare.
  * @param aOtherNode The node to compare it to.
  * @return True if they both nodes contain the same state, otherwise false.
  */
  //Checks if aNode and aOtherNode have the same state
  private boolean sameState(Node aNode, Node aOtherNode)
  {
    for(int i = 0; i < width; i++){
      for(int j = 0; j < height; j++){
        if(aNode.getState()[i][j] != aOtherNode.getState()[i][j]){
          return false;
        }
      }
    }
    return true;
  }

  /**
  * Checks if a node with the specified node's state is already in the fringe.
  * @param aNode The node to check.
  * @return True if there is already a node in the fringe with the same state as aNode, otherwise false.
  */
  protected boolean inFringe(Node aNode)
  {
    for(Node n: fringe){
      if(sameState(aNode, n)){
        return true;
      }
    }
    return false;
  }

  /**
  * Checks if a node with the specified node's state is already in the explored set.
  * @param aNode The node to check.
  * @return True if there is already a node in the explored set with the same state as aNode, otherwise false.
  */
  protected boolean inExploredSet(Node aNode)
  {
    for(Node n: exploredSet){
      if(sameState(aNode, n)){
        return true;
      }
    }
    return false;
  }

  /**
  * Prints the solution path to get to the goal state.
  * @param aNode The node containing the goal state.
  */
  protected void printSolution(Node aNode)
  {
    System.out.println(fileName + " " + method + " " + nodesFound);
    //get solution path
    ArrayList<Node> solutionPath = new ArrayList<Node>();
    Node nextNode = aNode;
    do{
      solutionPath.add(nextNode);
      nextNode = nextNode.getParent();
      }while(nextNode.getParent() != null);
      //print out solution
      while(solutionPath.size() > 0){
        Node printNode = solutionPath.get((solutionPath.size() - 1));
        Action printAction = printNode.getAction();
        System.out.print(printNode.getState()[printAction.getX() - 1][printAction.getY() - 1]); //print number moved
        System.out.print("@(" + printAction.getX() + "," + printAction.getY() + "); "); //print co-ordinates moved to
        solutionPath.remove(solutionPath.get((solutionPath.size() - 1)));
      }
      System.out.println();
      System.exit(1);
    }
  }