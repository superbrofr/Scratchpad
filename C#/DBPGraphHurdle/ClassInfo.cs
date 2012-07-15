using System.Collections.Generic;

// <summary>
// Used to store the information associated with each unique class in the input file. 
// Stores the class type, and a list of fan-out and fan-in classes.
// Used by ExtracFanIn in a hashmap where the index is the unique class name.
//
// Author: Charlotte Pierce [7182139]
// Version: 1.0 - Last modified 15/8/2011
// </summary>
public class ClassInfo
{
	private string classType;
	private List<string> fanOut; // list of classes used by the class
	private List<string> fanIn; // list of classes using the class
	
	public string Type
	{
		get
		{
			return classType;
		}
	}
	
	public List<string> FanOut
	{
		get
		{
			return fanOut;
		}
	}
	
	public List<string> FanIn
	{
		get
		{
			return fanIn;
		}
	}
	
	// <summary>
	// Creates a new set of class information.
  // <param name="aType">aType The type of the class.</param>
	// </summary>
	public ClassInfo(string aType)
	{
		classType = aType;
		fanOut = new List<string>();
		fanIn = new List<string>();
	}
	
	// <summary>
  // Adds the provided class name to the list of fan-in classes, if it isn't already in the list..
  // <param name="aClassName">Class name to add to the list.</param>
  // </summary>
	public void addFanIn(string aClassName)
	{
		if(!fanIn.Contains(aClassName))
			fanIn.Add(aClassName);
	}
	
	// <summary>
  // Adds the provided class name to the list of fan-out classes, if it isn't already in the list..
  // <param name="aClassName">Class name to add to the list.</param>
  // </summary>
	public void addFanOut(string aClassName)
	{
		if(!fanOut.Contains(aClassName))
			fanOut.Add(aClassName);
	}
}