using System;

class Variables
{
  static void Main()
  {
    //use 'bool' to declare a boolean variable
    bool affirmative = true; //when assigning a value to a bool, do NOT use capital letters
    bool negatory = false;
    
    Console.WriteLine("This statement, using the 'affirmative' variable, is {0}.", affirmative);
    Console.WriteLine("This statement, using the 'negatory' variable, is {0}.", negatory);
    
    //ARRAYS
    //Use 'new' to set the length of an array whose values are no yet known
    int[] myInts = {5, 10, 15}; //sets the values of the array
    bool[][] myBools = new bool[2][]; //essentailly an array of an array; length of first array is set
    //known as a 'jagged array'
    myBools[0] = new bool[2]; //length of array within first index of the 'outer' array is set
    myBools[1] = new bool[1]; //length of array within second index of the 'outer' array is set
    double[,] myDoubles = new double[2, 2]; //2-dimensional array, defined with comma, then length is set
    string[] myStrings = new string[3]; //single-dimensional array of strings
    
    //NOTE: Jagged arrays are rarely used, but can be useful in saving memory as they only allocate memory
    //for the length of the array held within the 'outer' index, rather than allocating a large block of memory
    //for a 'rectangular' 2-dimensional array, some of which may not be required.
  }
}