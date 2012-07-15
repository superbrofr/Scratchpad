/**
* An action that can be performed on the puzzle grid.
* Co-ordinates should represent the blank square that is going to be moved,
* in co-ordinate form rather than index form (i.e. top-left grid square is (1,1) not (0,0))
* @author Charlotte and Div
*/
public class Action
{
  int x, y; //co-ordinates of blank square to move - remember to add remove 1 to get the index in relation to the NM grid
  Direction dir; //direction to move it in
  
  /**
  * Creates a new action.
  * @param aX X-Co-ordinate of the puzzle grid square to be moved.
  * @param aY Y-Co-ordinate of the puzzle grid square to be moved.
  * @param aDir The direction in which to move the blank square.
  */
  public Action(int aX, int aY, Direction aDir)
  {
    x = aX;
    y = aY;
    dir = aDir;
  }
  
  /**
  * Returns the x-co-ordinate of the action.
  * @return The x-co-ordinate of the action.
  */
  public int getX()
  {
    return x;
  }
  
  /**
  * Returns the y-co-ordinate of the action.
  * @return The y-co-ordinate of the action.
  */
  public int getY()
  {
    return y;
  }
  
  /**
  * Returns the direction of the action.
  * @return The direction of the action.
  */
  public Direction getDirection()
  {
    return dir;
  }
}