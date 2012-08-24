public class in4DataGrid
{
  private int intWidth;
  private int intHeight;
  
  //Overloaded constructor with no parameters. The constructor initializes 
  //the values by calling the other constructor with the use of 'this' (referencing the containing class)
  //Known as a 'default constructor' - used if no parameters are input to give the variables in question
  //a default value.
  public in4DataGrid():this(200,300)
  {
    //Some Code Here.
  }
  
  //Overloaded constructor with parameters
  public in4DataGrid(int Width, int Height)
  {
    intWidth = Width;
    intHeight = Height;
    
    if(intWidth > intHeight)
    {
      //Some code here
    }
  }
}