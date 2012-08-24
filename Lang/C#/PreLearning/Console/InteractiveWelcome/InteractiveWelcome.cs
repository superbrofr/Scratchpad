using System;

class InteractiveWelcome
{
  static void Main()
  {
    Console.Write("What is your name?: ");
    Console.WriteLine("Hello, {0}.", Console.ReadLine());
  }
}