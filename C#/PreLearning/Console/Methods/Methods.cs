//static methods can be called simply by <classname>.<methodname>
//you must create an occurrence of an instance method to use its functionality
//therefore, you do not need to instantiate a class to use its static members, but you do to use its
//instance members

//static members are good for when there is a function to be performed, and no intermediate state is required
//e.g. mathematical calculatations

using System;

class OneMethod
{
    public static void Main()
    {
        string myChoice;
        OneMethod om = new OneMethod(); //must be initiated, because getChoice() is not specified as being static
                                        //and is therefore an 'instance' method, which means that the surrounding class
                                        //can be called many times, each time needing to create a new instance of any 
                                        //instance method which will be used in that calling of the class.
                                        
      //'par' [can be called anything], is a pointer, which points to an instnace of type OneMethod.
      //OneMethod is a class, so now we have a pointer (om), which refers to an instance of the class OneMethod, which
      //is located on the heap.
      
      // we need to create a new instance of OneMethod, so that the Main() method in OneMethod can make use of
      //getChoice(), located within itself, because, being static, THIS CANNOT MOVE FROM THE CODE, SO CREATES A NEW 
      //INSTANCE OF THE CLASS SO THAT IT CAN USE A METHOD LOCATED BELOW
       do
       {
            myChoice = om.getChoice();
            switch(myChoice)
            {
                case "y":
                    Console.WriteLine("Yes.");
                    break;
                case "n":
                    Console.WriteLine("No.");
                    break;
            }
            Console.WriteLine("press Enter key to continue...");
            Console.ReadLine();
        } while (myChoice != "y");
    }

    string getChoice() // 'string' is a declaration of the methods return type
    {
        string myChoice;
        Console.Write("Quit? [y/n]: ");
        myChoice = Console.ReadLine();
        return myChoice; //output of the method - must be same type as declared above
    }
}