import java.applet.*;
import java.awt.*;

// every applet must extend the Applet class
// applet's class name must be identical to the file name
public class DrawingLines extends Applet{
	int width, height;
	
	// gets executed when the applet starts
	// API: "Called by the browser or applet viewer to inform this 
	// applet that it has been loaded into the system."
	// --> The initialisation of the applet - must be overrided if there
	// is initialisation to perform - e.g. setting variables
	// otherwise does nothing
	public void init(){
		width = getSize().width; //stores width of applet
		height = getSize().height; //stores height of applet
		setBackground(Color.black); //sets the default background color
	}
	
	// executed whenever the applet is asked to draw itself
	// inherited from 'Container' class
	public void paint(Graphics g){
		g.setColor(Color.GREEN); //sets the colour of the drawn stuff
		//loop 10 times and draw each time
		for(int i = 0; i < 10; i++){
			//drawLine(int x1, int y1, int x2, int y2)
			//draws a line between (x1, y1) and (x2, y2)
			//NOTE: cartesian plane (with most grahpics programming) is
			//upside-down - origin is top left corner, x-axis increases to
			//the right, y-axes increasing downward
			g.drawLine(width, height, (i * width / 10), 0);
		}
	}
}