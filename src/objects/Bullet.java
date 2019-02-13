/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author kroko
 */
public class Bullet{
private final int r;
private final Point dirVec,pos;

    public Bullet(Point spawn, int r, Point dir) {
        this.pos = (Point) spawn.clone();
        this.r = r;
        this.dirVec = (Point) dir.clone();

    }

    public void update(){
        pos.translate((int)dirVec.getX(), (int)dirVec.getY());
    }
    
    
    public void paintBullet(Graphics2D g2d) {
        //rysowanie pocisku
        g2d.setColor(Color.white);
        g2d.fillOval((int)this.pos.getX(),(int)this.pos.getY(), r*2, r*2);
    }

    public int getX() {
        return (int) this.pos.getX();
    }

    public int getY() {
        return (int) this.pos.getY();
    }

    public int getR() {
        return r;
    }

    public Point getPos() {
        return pos;
    }
    
    public void showPos(){
        System.out.println("pos : " + this.pos);
    System.out.println("dir : " + this.dirVec);
    }
}
