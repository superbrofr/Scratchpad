using System;

class NamedWelcome
{
  static void Main(string[] arg)
  {
    string Name;
    Console.WriteLine("Hello {0}.", arg[0]);
    Console.Write("What is your name? ");
    Name = Console.ReadLine();
    Console.WriteLine("Hello then {0}.", Name);
  }
}

//The "{0}" parameter means that the next argument following the end quote will determine what goes 
//in that position.
//The index used when defining the {0} refers to which string input from the command line to return.
//The parameter name [identifier] (in this case 'nameofstring') is defined when the method is.