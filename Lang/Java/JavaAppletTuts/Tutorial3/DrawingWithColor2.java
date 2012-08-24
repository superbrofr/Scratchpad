import java.applet.*;
import java.awt.*;
import java.lang.Math;

public class DrawingWithColor2 extends Applet{
	int width, height;
	int n = 25;
	Color[] spectrum;
	
	public void init(){
		width = getSize().width;
		height = getSize().height;
		setBackground(Color.BLACK);
		
		spectrum = new Color[n];
		
		//create spectrum of color
		for(int i = 0; i < n; i++){
			//Color.HSBtoRGB(float hue, float saturation, float brightness)
			//converts the Hue, Saturation and Brightness to an appropriate
			//RGB representation
			//Saturation/Brightness between 0.0-1.0
			spectrum[i] = new Color(Color.HSBtoRGB(i/(float)n, 1, 1));
		}
	}
	
	public void paint(Graphics g){
		int radius = width / 3;
		for(int i = 0; i < n; i++){
			//computer (x, y) co-ords
			double angle = (2*Math.PI*i)/(double)n;
			int x = (int)(radius*Math.cos(angle));
			int y = (int)(radius*Math.sin(angle));
			
			g.setColor(spectrum[i]);
			g.drawString("Color", width/2+x, height/2+y);
		}
	}
}