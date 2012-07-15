import java.applet.*;
import java.awt.*;
//java.awt contains all of the classes required for actually drawing
//the applet (apart from 'paint'), like Graphics and Color

public class DrawingStuff extends Applet{

   int width, height;

	public void init(){
		width = getSize().width;
		height = getSize().height;
		setBackground(Color.BLACK);
	}

	public void paint(Graphics g) {
		g.setColor(Color.RED);
		//drawRect(int x, int y, int width, int height)
		g.drawRect(10, 20, 100, 15);
		g.setColor(Color.PINK);
		//fillRect(int x, int y , int width, int height)
		g.fillRect(240, 160, 40, 110);
	
		g.setColor(Color.BLUE);
		//drawOval(int x, int y, int width, int height)
		//NOTE: x and y are the upper left corner of the oval
		g.drawOval(50, 225, 100, 50);
		g.setColor(Color.ORANGE);
		//fillOval(int x, int y , int width, int height)
		g.fillOval(225, 37, 50, 25);
	
		g.setColor(Color.YELLOW);
		//drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
		//NOTE: x and y are the uppre left corner of the arc
		//		startAngle is the beginning angle
		//		arcAngle is the angular extent of the arc, relative to the start angle
		g.drawArc(10, 110, 80, 80, 90, 180);
		g.setColor(Color.CYAN);
		//fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)
		g.fillArc(140, 40, 120, 120, 90, 45);
	
		g.setColor(Color.MAGENTA);
		g.fillArc(150, 150, 100, 100, 90, 90);
		g.setColor(Color.BLACK);
		g.fillArc(160, 160, 80, 80, 90, 90);
	
		g.setColor(Color.GREEN);
		//drawString(String str, int x, int y)
		g.drawString("Groovy!", 50, 150);
	}
}

//NOTE: -> fillRect measures width and height by the pixel edges, so
//		the figure will be exactly the height and width (in pixels)
//		specified.
//		-> drawRect measures width and height by the center of the 
//		pixels, so the figure will be of the dimensions width + 1
//		by height + 1