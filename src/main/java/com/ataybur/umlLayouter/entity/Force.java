/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.entity;

/**
 *
 * @author atay
 */
public class Force {

    private Double X;
    private Double Y;

    public Force() {
	this.X = 0D;
	this.Y = 0D;
    }

    public Force(Double X, Double Y) {
	this.X = X;
	this.Y = Y;
    }

    public Double getX() {
	return X;
    }

    public void setX(Double X) {
	this.X = X;
    }

    public Double getY() {
	return Y;
    }

    public void setY(Double Y) {
	this.Y = Y;
    }

    @Override
    public String toString() {
	return "X: " + X + ", Y: " + Y;
    }

}
