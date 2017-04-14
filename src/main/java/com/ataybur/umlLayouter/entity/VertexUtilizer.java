package com.ataybur.umlLayouter.entity;

import com.ataybur.umlLayouter.util.ProjectConstants;

public class VertexUtilizer {
    private Vertex instance;
    
    public VertexUtilizer(Vertex instance){
	this.instance = instance;
    }
    
    public Vertex move(Force force) {
	Double xForceDistance = force.getX();
	Double yForceDistance = force.getY();
	Double xCoordinate = this.instance.getCoordinate().getX() + xForceDistance;
	Double yCoordinate = this.instance.getCoordinate().getY() + yForceDistance;
	this.instance.getCoordinate().setX(xCoordinate);
	this.instance.getCoordinate().setY(yCoordinate);
	return this.instance;
    }
    
    public Vertex moveForAlgorithm(Force force) {
  	Double xForceDistance = force.getX();
  	Double yForceDistance = force.getY();
  	Double xCoordinate = this.instance.getCoordinate().getX() + (ProjectConstants.C4 * xForceDistance);
  	Double yCoordinate = this.instance.getCoordinate().getY() + (ProjectConstants.C4 * yForceDistance);
  	this.instance.getCoordinate().setX(xCoordinate);
  	this.instance.getCoordinate().setY(yCoordinate);
  	return this.instance;
      }
    
    public Vertex create() {
	this.instance.setCoordinate(CoordinateUtilizer.createRandom());
	return this.instance;
    }

    public Vertex returnOneDegree(Vertex secondVertex) {
	if (this.instance.getDegree() <= secondVertex.getDegree()) {
	    return this.instance;
	} else {
	    return secondVertex;
	}
    }

}
