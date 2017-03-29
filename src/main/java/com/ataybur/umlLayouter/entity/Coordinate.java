/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.entity;

import com.ataybur.umlLayouter.util.ProjectConstants;

/**
 *
 * @author atay
 */
public class Coordinate {
    private Double x;
    private Double y;
    private int coordinateIterator;
    
    public Coordinate(Integer x,Integer y) {
        this.x = new Double(x);
        this.y = new Double(y);
        this.coordinateIterator = ProjectConstants.VERTEX_COUNTER;
    } 
    
     public Coordinate(Double x,Double y) {
        this.x = x;
        this.y = y;
        this.coordinateIterator = ProjectConstants.VERTEX_COUNTER;
    } 
    
    public Coordinate(Coordinate coordinate) {
        this.x = coordinate.getX();
        this.y = coordinate.getY();
        this.coordinateIterator = ProjectConstants.VERTEX_COUNTER;
    }    
    
    public Coordinate() {
        this.coordinateIterator = ProjectConstants.VERTEX_COUNTER;
    }    
    
    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(X: "+x+", Y: "+y+")";
    }

    @Override
    public boolean equals(Object obj) {
        Coordinate coordinate = (Coordinate) obj;
        return this.x.equals(coordinate.getX()) && this.y.equals(coordinate.getY());
    }
    
    
}
