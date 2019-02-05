/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import static asteroids.Asteroids.shiper;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JComponent;

/**
 * Klasa reprezentujaca Statek gracza
 *
 * @author kroko
 */
public class SpaceShip extends JComponent{

    public Point center, top, left, right, dirVector;
    public double distance, wingDistance;
    int accTime, maxAccTime = 20, shipAngle = -90;

    public SpaceShip(Point center, Point top, Point wing) {
        this.center = center;
        this.top = top;
        this.left = wing;
        this.right = new Point(wing);
        this.distance = center.distance(top);
        this.wingDistance = center.distance(left);
        this.dirVector = new Point(0, 0);
        accTime = maxAccTime;
    }

    public void turnShip(int direct) {
        shipAngle += direct * 15;
        calculateTurn(this.shipAngle, top, distance);
        calculateTurn(this.shipAngle - 120, left, wingDistance);
        calculateTurn(this.shipAngle + 120, right, wingDistance);
    }

    private void calculateTurn(int angle, Point calculatedPoint, double distance) {
        double radians = (angle * Math.PI / 180);
        double tempx = distance * Math.cos(radians);
        double tempy = distance * Math.sin(radians);
        calculatedPoint.move((int) Math.floor(tempx) + this.center.x, (int) Math.floor(tempy) + this.center.y);
    }

    public void showPiontPos(Point v) {
        System.out.println("left : " + v.getLocation());
        System.out.println("right : " + v.getLocation());
    }

    public void accelerate() {
        this.dirVector.setLocation((top.x - center.x) * 0.1, (top.y - center.y) * 0.1);
        accTime = 0;

    }

    public void update(int dir) {
        turnShip(dir);
        if (accTime < maxAccTime) {
            move(adjustForce());
            accTime++;
        }

        
    }

    public void checkEdges(JComponent canvas) {
        if (this.center.x < 0) {
            Point newPos = new Point(canvas.getWidth(), 0);
            move(newPos);
        }
        if (this.center.x > canvas.getWidth()) {
            Point newPos = new Point(-canvas.getWidth(), 0);
            move(newPos);
        }
        if (this.center.y < 0) {
            Point newPos = new Point(0, canvas.getHeight());
            move(newPos);
        }
        if (this.center.y > canvas.getHeight()) {
            Point newPos = new Point(0, -canvas.getHeight());
            move(newPos);
        }
    }

    private Point adjustForce() {
        if (accTime < maxAccTime / 2) {
            Point p = new Point(dirVector.x * accTime, dirVector.y * accTime);
            return p;
        } else {
            Point p = new Point(dirVector.x * (10 - (accTime % 10)), dirVector.y * (10 - (accTime % 10)));
            return p;
        }

    }

    public void move(Point dir) {

        top.translate(dir.x, dir.y);
        center.translate(dir.x, dir.y);
        left.translate(dir.x, dir.y);
        right.translate(dir.x, dir.y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
    
                Graphics2D g2d = (Graphics2D) g;

                //rysowanie statku
        g2d.setColor(Color.white);
        g2d.drawLine(this.center.x, this.center.y, this.top.x, this.top.y);
        g2d.drawLine(this.center.x, this.center.y, this.left.x, this.left.y);
        g2d.drawLine(this.center.x, this.center.y, this.right.x, this.right.y);
        g2d.drawLine(this.top.x, this.top.y, this.left.x, this.left.y);
        g2d.drawLine(shiper.top.x, shiper.top.y, shiper.right.x, shiper.right.y);

    }

    
}
