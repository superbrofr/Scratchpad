import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * ImageViewer is the main class of the image viewer application. It builds
 * and displays the application GUI and initialises all other components.
 * 
 * To start the application, create an object of this class.
 * 
 * @author Michael Kolling and David J Barnes 
 * @version 0.1
 */
public class ImageViewer
{
    private JFrame frame;
    
    /**
     * Create an ImageViewer show it on screen.
     */
    public ImageViewer()
    {
        makeFrame();
    }
    
    // ---- swing stuff to build the frame and all its components ----
    
    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame()
    {
        frame = new JFrame("ImageViewer");        
        Container contentPane = frame.getContentPane();
        
        JLabel label = new JLabel("I am a label.");
        contentPane.add(label);
        
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu filemenu = new JMenu("File");
        menubar.add(filemenu);
        
        JMenuItem openitem = new JMenuItem("Open");
        filemenu.add(openitem);

        JMenuItem quititem = new JMenuItem("Quit");
        filemenu.add(quititem);
        
        
        
        frame.pack();
        frame.setVisible(true);
    }
}
