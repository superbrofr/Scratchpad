// Switch statements are just like case statements in Pascal

using System;

class Switchy
{
  static void Main()
  {
    string Str;
    int Input;
    
    Console.Write("Please enter a number: ");
    Str = Console.ReadLine();
    Input = Int32.Parse(Str);
    
    switch (Input)
    {
      case 1:
        Console.WriteLine("You entered 1");
        break; //NOTE: You must put in break, so that the code doesn't fall down to the next case, even if there
               //is no command for this case.
               //break commands the program to ignore the rest of this block of code and move on.
      case 2:
        Console.WriteLine("You entered 2");
        break;
      case 3:
        Console.WriteLine("You entered 3");
        break;
      case 4:
        Console.WriteLine("You entered 4");
        break;
      default:
        Console.WriteLine("The number was not between 1 and 4");
        break;
    }
    
    Console.Write("Would you like to quit? [y/n]: ");
    Str = Console.ReadLine();
    
    switch (Str)
    {
      case "y":
        Console.WriteLine("Bye...");
        break;
      case "n":
        Console.WriteLine("Too bad...");
        break;
    }
  }
}

//You can put curly brackets around the commands in each case, but this isn't needed