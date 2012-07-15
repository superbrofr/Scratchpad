using System;

public class ParentClass
{
  public ParentClass()
  {
    Console.WriteLine("Parent Constructor.");
  }

  public void print()
  {
    Console.WriteLine("I'm a Parent Class.");
  }
}

//the colon specifies that the following identifier refers to the parent class
//i.e. the class from which the current class 'borrows' its code
//ChildClass now has all the functionality of ParentClass
//NOTE: Parent classes are always instantiated BEFORE derived [child] classes
public class ChildClass : ParentClass
{
  public ChildClass()
  {
    Console.WriteLine("Child Constructor.");
  }

  public static void Main()
  {
    ChildClass child = new ChildClass();
    child.print();
  }
}