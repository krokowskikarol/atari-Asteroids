/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import javax.swing.JComponent;

/**
 * Klasa reprezentujaca Statek gracza
 * @author kroko
 */
public class SpaceShip extends JComponent{
    public int x,y,x2,y2;
    public Rectangle r;
    public double radius;
    public SpaceShip(int x, int y, int x2, int y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        radius = checkDistance();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(80, 60);
    }
    
    public void turnShip(int a){
            
            double radians = (a *Math.PI / 180);
            double tempx = radius*Math.cos(radians);
            double tempy = radius*Math.sin(radians);
            
            this.x2 =(int) Math.floor(tempx) + x;
            System.out.println(x2);
            this.y2 =(int) Math.floor(tempy)+y;
            System.out.println(y2);
    }
    public double checkDistance() {
        double a, b, c;
        a = x - x2;
        b = y-y2;
        c =  Math.sqrt((a * a) + (b * b));
        
        return c;
    }

}

            

