using System;

public class DrawingObject
{
  public virtual void Draw() //'virtual' indicates that this method can be overridden by derived classes
  {
    Console.WriteLine("I'm just a generic drawing object.");
  }
}

public class Line : DrawingObject
{
  public override void Draw() //'override' allows the method to override a 'virtual' method in its base class
                              //overriding methods must have the same name and parameters as its base method
  {
    Console.WriteLine("I'm a Line.");
  }
}

public class Circle : DrawingObject
{
  public override void Draw()
  {
    Console.WriteLine("I'm a Circle.");
  }
}

public class Square : DrawingObject
{
  public override void Draw()
  {
    Console.WriteLine("I'm a Square.");
  }
}

public class DrawDemo
{
  public static int Main()
  {
    DrawingObject[] dObj = new DrawingObject[4];

    dObj[0] = new Line();
    dObj[1] = new Circle();
    dObj[2] = new Square();
    dObj[3] = new DrawingObject();

    foreach (DrawingObject drawObj in dObj)
    {
      drawObj.Draw();
    }

    return 0;
  }
}