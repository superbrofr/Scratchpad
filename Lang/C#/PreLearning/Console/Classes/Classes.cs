using System;

class OutputClass
{
    string myString;

    // Constructor - used to initialise data members of the class; in this case, that is myString
    //a default constructor is a constructor with no arguments
    //this is an INSTANCE BASED constructor, but constructors can also be STATIC (use 'static' instead of 'public')
    //NOTE: constructors can be private, or protected - when delcared as private, you cannot create an instance of the
    //class, or derive the class
    public OutputClass(string inputString) //constructors take parameters just like any method
    {
        myString = inputString;
    }

    // Instance Method
    public void printString() //'public' means that the class member can be accessed by other classes
    {
        Console.WriteLine("{0}", myString);
    }

    // Destructor
    ~OutputClass()
    {
        // Some resource cleanup routines
    }
}

class ExampleClass
{
    // Main begins program execution.
    public static void Main()
    {
        // Instance of OutputClass
        OutputClass outCl = new OutputClass("This is printed by the output class.");

        // Call Output class' method
        outCl.printString(); 
    }
}
