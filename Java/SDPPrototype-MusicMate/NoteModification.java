import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class NoteModification
{
  private String noteType;
  private BufferedImage noteImage;
  private int xCoord, yCoord;

  public NoteModification(String aNoteType, int x, int y)
  {
    try
    {
      noteImage = ImageIO.read(new File(aNoteType + ".gif"));
      noteType = aNoteType;
      xCoord = x;
      yCoord = y;
    }
    catch(IOException e)
    {
      System.out.println("Error creating new note.");
    }
  }

  public BufferedImage getImage()
  {
    return noteImage;
  }

  public int getX()
  {
    return xCoord;
  }

  public int getY()
  {
    return yCoord;
  }

  public String getNoteType()
  {
    return noteType;
  }
}