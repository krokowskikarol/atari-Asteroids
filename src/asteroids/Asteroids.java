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
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import objects.SpaceShip;
import objects.Bullet;
import objects.Rock;

/**
 *
 * @author kroko
 */
public class Asteroids extends JComponent implements ActionListener, KeyListener {

    public static SpaceShip ship = new SpaceShip(new Point(300, 300), new Point(300, 325), new Point(300, 312));
    public static ArrayList<Rock> asteroids;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Asteroids game = new Asteroids();
        JFrame window = new JFrame("Asteroids");
        window.add(game);
        window.addKeyListener(game);
        window.pack();

        asteroids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            asteroids.add(new Rock());
        }

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
        collisionCheck();
        ship.update();
        ship.checkEdges(this);

        System.out.println("liczba asteroid : "+ asteroids.size());
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g;

        // malowanie tła
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 800, 600);
        // malowanie statku
        ship.paintShip(g2d);
        // malowanie pocisków
        ship.magazine.forEach((var bullet) -> {
            bullet.paintBullet(g2d);
        });
        // malowanie asteroid
        asteroids.stream().map((asteroid) -> {
            asteroid.update();
            return asteroid;
        }).map((asteroid) -> {
            asteroid.checkEdges(this);
            return asteroid;
        }).forEachOrdered((asteroid) -> {
            asteroid.paintRock(g2d);
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
    public void collisionCheck(){
        for (Bullet bullet : ship.magazine) {
            for (Rock asteroid : asteroids) {
                if((bullet.getPos().distance(asteroid.getCenter())<= asteroid.getRadius())){
                      asteroid.add(new Rock(asteroid.getX(), asteroid.getY(), asteroid.getRadius()/2));
//                    asteroid.add(new Rock(asteroid.getX(), asteroid.getY(), asteroid.getRadius()/2));
                   // asteroid.remove(asteroid);
                    
                   
                     asteroids = new ArrayList<>(asteroids);
                     asteroids.add(new Rock());
                   asteroids.add(new Rock(0 ,0, 123));
                   
                   System.out.println("hit!!!");
                }
            }
            
        }
}
    
}
