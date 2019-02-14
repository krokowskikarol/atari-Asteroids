/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JComponent;

/**
 *
 * @author kroko
 */
public class Rock {

    private final Point center, dirVec;
    private final int radius;

    public Rock() {
        this.radius = 25;
//        ustalenie środka asteroidy
        this.center = new Point((int) (Math.random() * 700) + radius, (int) (Math.random() * 800) + radius);
        this.dirVec = new Point((int) (Math.random() * 8), (int) (Math.random() * 8));
    }

    public Rock(int x, int y, int radius) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.dirVec = new Point((int) (Math.random() * 8), (int) (Math.random() * 8));
    }

    public void update() {
        this.center.translate((int) dirVec.getX(), (int) dirVec.getY());
    }

    public void checkEdges(JComponent canvas) {
        if (this.center.x < 0) {
            center.translate(canvas.getWidth(), 0);
        }
        if (this.center.x > canvas.getWidth()) {
            center.translate(-canvas.getWidth(), 0);
        }
        if (this.center.y < 0) {
            center.translate(0, canvas.getHeight());
        }
        if (this.center.y > canvas.getHeight()) {
            center.translate(0, -canvas.getHeight());
        }
    }

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public int getX() {
        return (int) this.center.getX();
    }

    public int getY() {
        return (int) this.center.getY();
    }

    public void paintRock(Graphics2D g2d) {
        // malowanie skaly
        g2d.setColor(Color.white);
        g2d.drawOval((int) center.getX() - radius, (int) center.getY() - radius, radius * 2, radius * 2);
        g2d.fillOval((int) center.getX(), (int) center.getY(), 2, 2);
    }
}
