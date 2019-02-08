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

/**
 *
 * @author kroko
 */
public class Bullet extends JComponent{
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
    
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g;

        //rysowanie pocisku
        g2d.setColor(Color.white);
        g2d.fillOval((int)this.pos.getX(),(int)this.pos.getY(), r*2, r*2);
    
    }

@Override
    public int getX() {
        return (int) this.pos.getX();
    }

@Override
    public int getY() {
        return (int) this.pos.getY();
    }

    public int getR() {
        return r;
    }
    public void showPos(){
        System.out.println("pos : " + this.pos);
    System.out.println("dir : " + this.dirVec);
    }
}
