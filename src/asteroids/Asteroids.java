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
import objects.Bullet;

/**
 *
 * @author kroko
 */
public class Asteroids extends JComponent implements ActionListener, KeyListener {

    public static SpaceShip ship = new SpaceShip(new Point(300, 300), new Point(300, 325), new Point(300, 312));

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Asteroids game = new Asteroids();
        JFrame window = new JFrame("Asteroids");
        window.add(game);
        window.addKeyListener(game);
        window.pack();

        //dodanie statku gracza
        window.add(ship, 0);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //zainicjowanie i uruchomienie timera
        Timer t = new Timer(50, game);
        t.start();

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        ship.update();
        ship.checkEdges(this);

        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 800, 600);

        ship.magazine.forEach((b) -> {
            g2d.setColor(Color.WHITE);
            g2d.fillOval(b.getX(), b.getY(), b.getR(), b.getR());
        });
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                ship.setDir(1);
                break;
            case KeyEvent.VK_LEFT:
                ship.setDir(-1);
                break;
            case KeyEvent.VK_SPACE:
                ship.fire();
                break;
        }
        if (keyCode == KeyEvent.VK_CONTROL) {
            ship.accelerate();
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT) {
            ship.setDir(0);
        }
        if (keyCode == KeyEvent.VK_CONTROL) {
            ship.setIsFlying(false);
        }

    }

}
