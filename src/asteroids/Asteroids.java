/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import objects.SpaceShip;

/**
 *
 * @author kroko
 */
public class Asteroids extends JComponent implements ActionListener{

    public static SpaceShip ship = new SpaceShip(400, 300,400,350);
    public int count = 1;
    public static SpaceShip ship1 = new SpaceShip(300, 300,40,50);
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Asteroids game = new Asteroids();
        JFrame window = new JFrame("Asteroids");
        window.add(game);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
    //zainicjowanie i uruchomienie timera
        Timer t = new Timer(10, game);
        t.start();
        
        window.add(ship);
        window.add(ship1);
        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(count<90){
            ship.turnShip(count);
            count++;
        }
        else{
            count = 0;
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 800, 600);
        
        g2d.setColor(Color.white);
        g2d.drawLine(ship.x, ship.y, ship.x2, ship.y2);
        
    }

}
