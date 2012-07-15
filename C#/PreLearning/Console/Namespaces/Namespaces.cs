using System;
using IAmANameSpace.IAmToo;
using ns = IAmANameSpace.IAmToo; //ns is used as an alias - methods/classes within all on the RHS of the equals can be
//referred to with "ns.<identifier>"

namespace MyFirstNamespace
{
  class First
  {
    public static void Message()
    {
      Console.WriteLine("Look at me!");
    }
  }
}

namespace IAmANameSpace
{
  //namespaces can be nested
  namespace IAmToo
  {
    class LookAtMe
    {
      public static void Message()
      {
        Console.WriteLine("I am inside a nested namespace");
      }
    }
  }
}

//you can call a method from within a namespace by simply referring to it

namespace Program
{
  class NameSpaceCalling
  {
    public static void Main()
    {
      MyFirstNamespace.First.Message();
      IAmANameSpace.IAmToo.LookAtMe.Message();
      LookAtMe.Message(); //the above line is shortened by the extra 'using' line at the top of the program
      ns.LookAtMe.Message();
    }
  }
}