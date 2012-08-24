using System;

class Loops
{
  static void Main()
  {
    //THE WHILE LOOP [pre-condition]
    Console.WriteLine("-----> The while Loop <-----");
    int Input;
    Console.Write("Enter a number less than 10: ");
    Input = Int32.Parse(Console.ReadLine());
    
    while (Input < 20)
    {
      Console.WriteLine("The number is now: {0}", Input);
      Input++;
    }
    
    //THE DO LOOP [post-condition]
    Console.WriteLine("-----> The do Loop <-----");
    string Choice;
    
    do
    {
      Console.Write("Do you wish to exit this loop? [y/n]:");
      Choice = Console.ReadLine();
    } while (Choice != "y");
    Console.WriteLine("Well done.");
    
    //THE FOR LOOP
    Console.WriteLine("-----> The for Loop <-----");
    for (int i = 0, i2 = 1; i <= 10; i++, i2++)
    {
      Console.WriteLine("i is now {0}, in number {1} iteration of this loop", i, i2);
    }
    
    //THE FOREACH LOOP
    Console.WriteLine("-----> The foreach Loop <-----");
    string[] states = {"Victoria", "NSW", "Queensland", "Tasmania", "Western Australia", "Northern Territory"};
    
    foreach (string state in states) //'state' is the identifier used for each item in th list as it is worked through
    {
      Console.WriteLine("One state is {0} ", state);
    }
  }
}