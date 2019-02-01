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

    public static SpaceShip ship = new SpaceShip(400, 300,400,325);
    public int count = 1;
    public static SpaceShip ship1 = new SpaceShip(400, 300,400,312);
    public static SpaceShip ship3 = new SpaceShip(400, 300,400,312);
    

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
        Timer t = new Timer(100, game);
        t.start();
        
        window.add(ship);
        window.add(ship1);
         window.add(ship3);
        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
            ship.turnShip(count);
            ship1.turnShip(count-120);
            ship3.turnShip(count+120);
            count+=15;
        
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 800, 600);
        
        g2d.setColor(Color.white);
        //linia centrum
        g2d.drawLine(ship.x, ship.y, ship.x2, ship.y2);
        // podstawa
g2d.drawLine(ship1.x, ship1.y,ship1.x2, ship1.y2);
        g2d.drawLine(ship3.x, ship3.y,ship3.x2, ship3.y2);
                
//g2d.drawLine(ship3.x2, ship3.y2, ship1.x2, ship1.y2);
        //linie boczne
        g2d.drawLine(ship1.x2, ship1.y2,ship.x2, ship.y2);
        g2d.drawLine(ship3.x2, ship3.y2,ship.x2, ship.y2);
        
        
    }

}
