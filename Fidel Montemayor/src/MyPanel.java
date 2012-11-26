import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge
 */
public class MyPanel extends JComponent{
    private BufferedImage bi;
    
    public MyPanel(String archivo, boolean resize){
        try{
            bi=ImageIO.read(new File(archivo));
            if(resize)  setPreferredSize(new Dimension(bi.getWidth(),bi.getHeight()));
            else{
                Toolkit tk = Toolkit.getDefaultToolkit();
                int xSize = ((int) tk.getScreenSize().getWidth());  
                int ySize = ((int) tk.getScreenSize().getHeight());  
                //this.setSize(xSize,ySize); 
                //
                setPreferredSize(new Dimension(800,600));
            }
        }catch(IOException ax){
            ax.printStackTrace();
        }
        
    }
    public void paintComponent(Graphics g){
        Dimension d=getSize();
        g.drawImage(bi,0,0,d.width,d.height,this);
    }
    
    
    /*public Prueba(){
         try {
             bi = ImageIO.read(
              new URL("http://rabbitbrush.frazmtn.com/kittens.jpg"));
             setPreferredSize(
              new Dimension(bi.getWidth(),bi.getHeight()));
         } catch (IOException ioe) {
         }
     }

     public void paintComponent(Graphics g) {
         g.drawImage(bi,0,0,null);
     }*/
}
