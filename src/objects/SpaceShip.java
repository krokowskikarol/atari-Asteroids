/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JComponent;
import java.util.ArrayList;

/**
 * Klasa reprezentujaca Statek gracza
 *
 * @author kroko
 */
public class SpaceShip extends JComponent {

    private Point center, top, left, right, dirVector, fireVec;
    private double distance, wingDistance;
    private int dir = 0, shipAngle = -90, magazineSize = 10;
    private boolean isFlying = false;
    //arbitralnie wybrany czas po jakim mozna ponownie poruszyc statkiem
    private int friction = 20;
    public ArrayList<Bullet> magazine;
    private final int maxSpeed = 20;

    public SpaceShip(Point center, Point top, Point wing) {
        this.center = center;
        this.top = top;
        this.left = wing;
        this.right = new Point(wing);
        this.distance = center.distance(top);
        this.wingDistance = center.distance(left);
        this.dirVector = new Point(0, 0);
        this.fireVec = new Point();
        this.magazine = new ArrayList<>();
//        for (int i = 0; i < magazineSize; i++) {
//            this.magazine.add(new Bullet(center, 2, fireVec));
//        }
        this.magazine.clear();
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

    public void accelerate() {
        if (!isFlying) {
            calculateDirVec();
            isFlying = true;
        }
    }

    public void update() {
        turnShip(this.dir);

        adjustAcceleration();
        move(dirVector);

        //update pociskow
        magazine.forEach((b) -> {
            b.update();
        });
    }

    private void adjustAcceleration() {
        if (isFlying && (Math.abs(this.dirVector.getX()) < maxSpeed) && (Math.abs(this.dirVector.getY()) < maxSpeed)) {
            this.dirVector.translate(Integer.signum((int) this.dirVector.getX()), Integer.signum((int) this.dirVector.getY()));
        } else if (!isFlying) {
            if (friction % 5 == 0) {
                this.dirVector.translate(-Integer.signum((int) this.dirVector.getX()), -Integer.signum((int) this.dirVector.getY()));
            }
        }
        friction--;
    }

    private void calculateDirVec() {
        this.dirVector.setLocation((top.x - center.x) * 0.1, (top.y - center.y) * 0.1);
    }

    private void calculateFireVec() {
        this.fireVec.setLocation((top.x - center.x), (top.y - center.y));
    }

    public void fire() {
        if (magazine.size() > magazineSize) {
            magazine.remove(0);
        }
        calculateFireVec();
        Bullet e = new Bullet(this.top, 2, this.fireVec);
        magazine.add(e);
    }

    public void checkEdges(JComponent canvas) {
        if (this.center.x < 0) {
            move(new Point(canvas.getWidth(), 0));
        }
        if (this.center.x > canvas.getWidth()) {
            move(new Point(-canvas.getWidth(), 0));
        }
        if (this.center.y < 0) {
            move(new Point(0, canvas.getHeight()));
        }
        if (this.center.y > canvas.getHeight()) {
            move(new Point(0, -canvas.getHeight()));
        }
    }

    public void move(Point dir) {
        top.translate(dir.x, dir.y);
        center.translate(dir.x, dir.y);
        left.translate(dir.x, dir.y);
        right.translate(dir.x, dir.y);
    }

//    @Override
    public void paintShip(Graphics2D g2d) {
        //rysowanie statku
        g2d.setColor(Color.white);
        g2d.drawLine(this.center.x, this.center.y, this.top.x, this.top.y);
        g2d.drawLine(this.center.x, this.center.y, this.left.x, this.left.y);
        g2d.drawLine(this.center.x, this.center.y, this.right.x, this.right.y);
        g2d.drawLine(this.top.x, this.top.y, this.left.x, this.left.y);
        g2d.drawLine(this.top.x, this.top.y, this.right.x, this.right.y);

    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void setIsFlying(boolean isFlying) {
        this.isFlying = isFlying;
    }

}
