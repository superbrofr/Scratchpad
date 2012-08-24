using System;

class Selection
{
  static void Main()
  {
    string StrInput;
    int IntInput1, IntInput2;
    
    Console.Write("Please enter a number: ");
    StrInput = Console.ReadLine();
    IntInput1 = Int32.Parse(StrInput);
    Console.Write("Please enter another number: ");
    StrInput = Console.ReadLine();
    IntInput2 = Int32.Parse(StrInput);
    Console.WriteLine("You entered: {0} and {1}", IntInput1, IntInput2);
    
    if (IntInput1 > IntInput2)
      Console.WriteLine("The first number is larger than the second");
    else if ((IntInput1 == IntInput2) || (IntInput1 == 1))
      Console.WriteLine("The numbers are the same, or the first number was 1");
    else if (IntInput1 != 0)
      Console.WriteLine("The first number is not equal to zero");
      
    if (IntInput1 != 0 && IntInput2 != 0)
      Console.WriteLine("Neither number is zero");
    else
      Console.WriteLine("One of the numbers is zero");
  }
}