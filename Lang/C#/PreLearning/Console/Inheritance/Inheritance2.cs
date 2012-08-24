//a derived class is a specialisation of its base class
using System;

public class Parent
{
  string parentString;
  public Parent()
  {
    Console.WriteLine("Parent Constructor.");
  }
  
  public Parent(string myString)
  {
    parentString = myString;
    Console.WriteLine(parentString);
  }
  
  public void print()
  {
    Console.WriteLine("I'm a Parent Class.");
  }
}

public class Child : Parent
{
  public Child() : base("From Derived") //calls the base class constructor; the constructer with appropriate paramteters
  //will be found and executed, else the default constructor will be used
  {
    Console.WriteLine("Child Constructor.");
  }
  
  public new void print() //if print() is called, this version of it overrides that contained in the base class
  //the 'new' keyword explicitly states that this method is to override that in the base class with the same name
  //if they keyword isn't there, it implies that the coder wishes polymorphism to occur
  {
    base.print(); //to use print() from the bass class, it must be specified that it is from the bass class
    Console.WriteLine("I'm a Child Class.");
  }
  
  public static void Main()
  {
    Child child = new Child();
    child.print();
    ((Parent)child).print(); //another way of calling the base print method
  }
}