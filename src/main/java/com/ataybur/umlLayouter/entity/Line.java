/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.entity;

/**
 *
 * @author atay
 */
public class Line {

    private Coordinate firstPoint;
    private Coordinate secondPoint;
    private Double slope;
    private Double intercept;

    public Line(Coordinate firstPoint, Coordinate secondPoint) {
	this.firstPoint = firstPoint;
	this.secondPoint = secondPoint;
	slope = (this.firstPoint.getY() - this.secondPoint.getY()) / (this.firstPoint.getX() - this.secondPoint.getX());
	intercept = this.firstPoint.getY() - (slope * this.firstPoint.getX());
    }

    public Line(Vertex firstPoint, Vertex secondPoint) {
	this.firstPoint = firstPoint.getCoordinate();
	this.secondPoint = secondPoint.getCoordinate();
	if (firstPoint.getCoordinate().getX() != null && secondPoint.getCoordinate().getX() != null) {
	    slope = (this.firstPoint.getY() - this.secondPoint.getY()) / (this.firstPoint.getX() - this.secondPoint.getX());
	    intercept = this.firstPoint.getY() - (slope * this.firstPoint.getX());
	}
    }

    public Coordinate getFirstPoint() {
	return firstPoint;
    }

    public void setFirstPoint(Coordinate firstPoint) {
	this.firstPoint = firstPoint;
    }

    public Coordinate getSecondPoint() {
	return secondPoint;
    }

    public void setSecondPoint(Coordinate secondPoint) {
	this.secondPoint = secondPoint;
    }

    public Double getSlope() {
	return slope;
    }

    public void setSlope(Double slope) {
	this.slope = slope;
    }

    public Double getIntercept() {
	return intercept;
    }

    public void setIntercept(Double intercept) {
	this.intercept = intercept;
    }

    @Override
    public String toString() {
	return "(" + this.getFirstPoint() + " - " + this.getSecondPoint() + ")";
    }

}
