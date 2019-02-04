/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Point;

/**
 * Klasa reprezentujaca Statek gracza
 *
 * @author kroko
 */
public class SpaceShip {

    public Point center, top, left, right, dirVector;
    public double distance, wingDistance;
    int accTime = 0, maxAccTime = 20, shipAngle = -90; 
    

    public SpaceShip(Point center, Point top, Point wing) {
        this.center = center;
        this.top = top;
        this.left = wing;
        this.right = new Point(wing);
        this.distance = center.distance(top);
        this.wingDistance = center.distance(left);
        this.dirVector = new Point(0, 0);
    }

    

    public void turnShip(int direct) {
        shipAngle += direct*15;
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
        this.dirVector.setLocation((top.x-center.x), (top.y-center.y));
        accTime = 0;
        
    }
    public void update(int dir){
         turnShip(dir);
          if(accTime<maxAccTime/2){
            move(adjustForce(dirVector,accTime));
            }
          else if(accTime<maxAccTime){
            move(adjustForce(dirVector, accTime));
            }
        accTime ++;
    }
    private Point adjustForce(Point force, int mod){
        Point p = new Point(force.x*mod, force.y*mod);
        
        return p;
    }
    public void move(Point dir){
        
        top.translate(dir.x, dir.y);
        center.translate(dir.x, dir.y);
        left.translate(dir.x, dir.y);
        right.translate(dir.x, dir.y);
    }
    

}
