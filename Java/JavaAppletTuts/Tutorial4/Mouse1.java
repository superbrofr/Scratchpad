//easy to use inner event listeners (not anonymous inner event listeners for mouse
//events because there are not multiple mice, therefore each event has the
//the same reaction, unlike with buttons
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Mouse1 extends Applet implements MouseListener, MouseMotionListener{
	int width, height;
	int mx, my; //mouse co-ords
	boolean isButtonPressed = false;
	
	public void init(){
		width = getSize().width;
		height = getSize().height;
		setBackground(Color.BLACK);
		
		mx = width/2;
		my = height/2;
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void mouseEntered(MouseEvent e){
		//
	}
	public void mouseExited(MouseEvent e){
		//
	}
	
	public void mouseClicked(MouseEvent e){
		isButtonPressed = true;
	//	setBackground(Color.GRAY);
		repaint(); //calls 'paint' method ASAP, or update method ASAP
		e.consume(); //consumes the event - will not be processed in the default manner by the source which originated it
	}
	
	public void mousePressed(MouseEvent e){
		isButtonPressed = true;
		setBackground(Color.GRAY);
		repaint();
		e.consume();
	}
	
	public void mouseReleased( MouseEvent e ) {
		isButtonPressed = false;
		setBackground(Color.BLACK);
		repaint();
		e.consume();
   }
	
	public void mouseMoved(MouseEvent e){
		mx = e.getX(); //gets x-coord of the mouse
		my = e.getY(); //gets y-coord of the mouse
		showStatus("Mouse at (" + mx + "," + my + ")");
		repaint();
		e.consume();
	}
	
	public void mouseDragged(MouseEvent e){
		mx = e.getX();
		my = e.getY();
		showStatus("Mouse at (" + mx + "," + my + ")");
		repaint();
		e.consume();
	}
	
	public void paint(Graphics g) {
		if(isButtonPressed){
			g.setColor(Color.BLACK);
		}
		else{
			g.setColor(Color.GRAY);
		}
		g.fillRect(mx - 20, my - 20, 40, 40);
	}
}