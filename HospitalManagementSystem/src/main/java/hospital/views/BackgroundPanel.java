/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital.views;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ITACHI
 */
public class BackgroundPanel extends JPanel{
    private Image backgroundImage;
    
    public BackgroundPanel(String imagePath){
        backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
                if (backgroundImage == null) {
                    System.out.println("❌ Background image NOT FOUND!");
                }else {
                    System.out.println("✅ Background image loaded successfully.");
                }
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(backgroundImage != null){
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } 
    }
    
}
