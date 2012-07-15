import java.util.ArrayList;

/**
* Used to store the information associated with each unique class in the input file. 
* Stores the class type, and a list of fan-out and fan-in classes.
* Used by ExtracFanIn in a hashmap where the index is the unique class name.
* 
* @author Charlotte Pierce [7182139]
* @version 1.0 - Last modified 15/8/2011
*/
public class ClassInfo
{
  private String classType;
  private ArrayList<String> fanOut; // list of classes used by the class
  private ArrayList<String> fanIn; // list of classes using the class

  /**
  * Creates a new set of class information.
  * @param aType The type of the class.
  */
  public ClassInfo(String aType)
  {
    classType = aType;
    fanOut = new ArrayList<String>();
    fanIn = new ArrayList<String>();
  }

  /**
  * Adds the provided class name to the list of fan-in classes, if it isn't already in the list.
  * @param aClassName Class name to add to the list.
  */
  public void addFanIn(String aClassName)
  {
		if(!fanIn.contains(aClassName))
			fanIn.add(aClassName);
  }

  /**
  * Adds to provided class name to the list of fan-out classes, if it isn't already in the list.
  * @param aClassName Class name to add to the list.
  */
  public void addFanOut(String aClassName)
  {
		if(!fanOut.contains(aClassName))
			fanOut.add(aClassName);
  }

  /**
  * Returns the stored class type.
  * @return The stored class type.
  */
  public String getClassType()
  {
    return classType;
  }

  /**
  * Returns the list of fan-out classes.
  * @return The list of fan-out classes.
  */
  public ArrayList<String> getFanOut()
  {
    return fanOut;
  }

  /**
  * Returns the lsit of fan-in classes.
  * @return The list of fan-in classes.
  */
  public ArrayList<String> getFanIn()
  {
    return fanIn;
  }
}