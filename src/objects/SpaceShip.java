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
import java.util.ArrayList;

/**
 * Class representing player spaceship
 *
 * @author kroko
 */
public class SpaceShip {

    private final Point center, top, left, right, shieldGenerator, dirVector, fireVec;
    private final double distance, wingDistance;
    private final int magazineSize = 10, shieldRadius = 18;
    private int dir = 0, shipAngle = -90;
    private boolean isFlying = false;
    //shield capacity
    private double shields = 100;
    //arbitralnie wybrany czas po jakim mozna ponownie poruszyc statkiem
    private int friction = 20;
    public ArrayList<Bullet> magazine;
    private final int maxSpeed = 20;

    /**
     * Constructor building player spaceship
     *
     * @param center Point describing center and pivot point of the ship
     */
    public SpaceShip(Point center) {
        this.center = center;
        this.top = new Point((int) center.getX(), (int) center.getY() + 25);
        this.left = new Point((int) center.getX(), (int) center.getY() + 12);
        this.right = new Point();
        this.distance = this.center.distance(this.top);
        this.wingDistance = this.center.distance(this.left);
        this.shieldGenerator = new Point();
        this.dirVector = new Point(0, 0);
        this.fireVec = new Point();
        this.magazine = new ArrayList<>();
        this.magazine.clear();
    }

    /**
     * Calculates the new position of 4 points building a ship based on a given
     * direction
     *
     * @param direction integer value describing in which direction ship will
     * turn: 1 for clockwise turn, -1 for counter-clockwise and 0 for no turn
     */
    public void turnShip(int direction) {
        shipAngle += direction * 10;
        // 120 and - 120 deviation based on the personal taste of the ship's design
        calculateTurn(this.shipAngle, top, distance);
        calculateTurn(this.shipAngle - 120, left, wingDistance);
        calculateTurn(this.shipAngle + 120, right, wingDistance);
        calculateTurn(this.shipAngle, this.shieldGenerator, distance / 3);
    }

    /**
     * Calculating new position of given point based on given angle and distance
     * from pivot point (based on trygonometry)
     *
     * @param angle integer value describing angle of rotation
     * @param calculatedPoint Point which new position will be calculated
     * @param distance double value of distance between pivot point and
     * calculatedPoint
     */
    private void calculateTurn(int angle, Point calculatedPoint, double distance) {
        double radians = (angle * Math.PI / 180);
        double tempx = distance * Math.cos(radians);
        double tempy = distance * Math.sin(radians);
        calculatedPoint.move((int) Math.floor(tempx) + this.center.x, (int) Math.floor(tempy) + this.center.y);
    }

    /**
     * The function that puts the ship in motion if it is no longer in motion
     * and calculates direction vector
     */
    public void accelerate() {
        if (!isFlying) {
            calculateDirVec();
            isFlying = true;
        }
    }

    /**
     * Updating the status of the ship, its rotation, acceleration, 
     * location, recharging shields and updating the status of missiles in the magazine
     */
    public void update() {
        turnShip(this.dir);

        adjustAcceleration();
        move(dirVector);
        rechargeShields();
        //update pociskow
        magazine.forEach((b) -> {
            b.update();
        });
    }

/**
 * In every frame when shields are below maximum value, function increase they value by 0.2
 */
    private void rechargeShields() {
        if (shields <= 100) {
            shields += 0.2;
        }
    }
/**
 * While ship is flying in every frame it will accelerate by 1 until reach max Speed value,
 * and while wasn't flying it will loose momentum by 1 per 5 animation frames
 */
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
/**
 * calculating direction vector by checking differences between top and center x/y values and then multiplying them by 0.1
 */
    private void calculateDirVec() {
        this.dirVector.setLocation((top.x - center.x) * 0.1, (top.y - center.y) * 0.1);
    }
/**
 * calculating fire direction vector by checking differences between top and center x/y values and then multiplying them by 0.5
 */
    private void calculateFireVec() {
        this.fireVec.setLocation((top.x - center.x) * 0.5, (top.y - center.y) * 0.5);
    }

    /**
     * creating and adding Bullet to the magazine if magzine capacity is exceeded 
     * the first Bullet will be destroyed (removed)
     */
    public void fire() {
        if (magazine.size() > magazineSize) {
            magazine.remove(0);
        }
        calculateFireVec();
        Bullet e = new Bullet(this.center, 2, this.fireVec);
        magazine.add(e);
    }
/**
 * checking if the center point of ship is in canvas boundaries and adjusting it position if needed
 * @param canvas JComponent object which will paint the ship
 */
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

    /**
     * changing position of Points building ship by a given Point (2d vector )
     * @param dir Point which work as direction vector
     */
    public void move(Point dir) {
        top.translate(dir.x, dir.y);
        center.translate(dir.x, dir.y);
        left.translate(dir.x, dir.y);
        right.translate(dir.x, dir.y);
        shieldGenerator.translate(dir.x, dir.y);
    }

    /**
     * paining ship 
     * @param g2d Graphics2D of game canvas class
     */
    public void paintShip(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.drawOval(shieldGenerator.x - shieldRadius, shieldGenerator.y - shieldRadius, shieldRadius * 2, shieldRadius * 2);

        //rysowanie statku
        g2d.setColor(Color.white);
        g2d.drawLine(this.center.x, this.center.y, this.top.x, this.top.y);
        g2d.drawLine(this.center.x, this.center.y, this.left.x, this.left.y);
        g2d.drawLine(this.center.x, this.center.y, this.right.x, this.right.y);
        g2d.drawLine(this.top.x, this.top.y, this.left.x, this.left.y);
        g2d.drawLine(this.top.x, this.top.y, this.right.x, this.right.y);

        g2d.setColor(Color.BLUE);
        g2d.fillRect(30, 30, (int) shields * 2, 20);
    }
/**
 * dir setter
 * @param dir Integer value 
 */
    public void setDir(int dir) {
        this.dir = dir;
    }
/**
 * checking for collisionn between shield and given ArrayList of Rocks 
* collision check is based on distance between center points of shield Generator and given Rock
* if distance is less than they combined radius we get hit
* @param rocks ArrayList of Rock which will be chcecked for collision
 */
    public void checkForCollision(ArrayList<Rock> rocks) {
        for (Rock rock : rocks) {
            if (rock.getCenter().distance(shieldGenerator) < rock.getRadius() + shieldRadius) {
                this.shields -= 5;
            }
        }
    }
/**
 * standard setter for variable
 * @param isFlying boolean value 
 */
    public void setIsFlying(boolean isFlying) {
        this.isFlying = isFlying;
    }

}
