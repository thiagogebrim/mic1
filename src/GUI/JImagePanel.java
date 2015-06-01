/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
<<<<<<< HEAD
import java.io.InputStream;
=======
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author raksamary
 */
public class JImagePanel extends JPanel{
    private BufferedImage image;
    
    
    public JImagePanel(String fileName){
        try {
<<<<<<< HEAD
            InputStream in = JImagePanel.class.getClassLoader().getResourceAsStream("GUI/"+fileName);
            image= ImageIO.read(in);
            //  instr = Utils.class.getResourceAsStream("car.jpg");
            
            
            Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        } catch (IOException ex) {
            Logger.getLogger(JImagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
=======
            File test = new File(JImagePanel.class.getResource(fileName).toURI());
            image= ImageIO.read(test);
        } catch (URISyntaxException | IOException  ex) {
            Logger.getLogger(JImagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }
}
