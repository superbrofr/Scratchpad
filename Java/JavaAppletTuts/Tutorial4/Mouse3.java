import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Mouse3 extends Applet implements MouseListener, MouseMotionListener
{
	int width, height;
	//use Vector, not array because it allows appending and deletion 
	//more easily than arrays
	Vector<Point> listOfPositions; //list of mouse positions
	public void init(){
		width = getSize().width;
		height = getSize().height;
		setBackground(Color.BLACK);
		listOfPositions = new Vector<Point>();
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
	
	public void mouseMoved(MouseEvent e){
		if(listOfPositions.size() >= 50){
			listOfPositions.removeElementAt(0); //remove first element (i.e. oldest point)
		}
		
		//add new position to end of list
		//Point is a 2D point with (x, y) co-ords
		listOfPositions.addElement(new Point(e.getX(), e.getY()));
		
		repaint();
		e.consume();
	}
	
	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		for(int j = 1; j < listOfPositions.size(); j++){
			Point A = (Point)(listOfPositions.elementAt(j - 1));
			Point B = (Point)(listOfPositions.elementAt(j));
			g.drawLine(A.x, A.y, B.x, B.y);
		}
	}
}