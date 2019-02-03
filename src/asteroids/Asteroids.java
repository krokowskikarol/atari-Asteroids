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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import objects.SpaceShip;

/**
 *
 * @author kroko
 */
public class Asteroids extends JComponent implements ActionListener, KeyListener {

    public int count = 0;

    public static SpaceShip shiper = new SpaceShip(new Point(300, 300), new Point(300, 325), new Point(300, 312));

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Asteroids game = new Asteroids();
        JFrame window = new JFrame("Asteroids");
        window.add(game);
            window.addKeyListener(game);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //zainicjowanie i uruchomienie timera
        Timer t = new Timer(50, game);
        t.start();

        //window.add(shiper);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        shiper.turnShip(count);
        shiper.showPiontPos();
        System.out.println(count);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 800, 600);

        g2d.setColor(Color.white);
        //rysowanie statku
        g2d.drawLine(shiper.center.x, shiper.center.y, shiper.top.x, shiper.top.y);
        g2d.drawLine(shiper.center.x, shiper.center.y, shiper.left.x, shiper.left.y);
        g2d.drawLine(shiper.center.x, shiper.center.y, shiper.right.x, shiper.right.y);
        g2d.drawLine(shiper.top.x, shiper.top.y, shiper.left.x, shiper.left.y);
        g2d.drawLine(shiper.top.x, shiper.top.y, shiper.right.x, shiper.right.y);

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                this.count = 1;
                break;
            case KeyEvent.VK_LEFT:
                this.count = -1;
                break;
            }
                
        }
    

    @Override
    public void keyReleased(KeyEvent event) {
        count = 0;
    }

}
