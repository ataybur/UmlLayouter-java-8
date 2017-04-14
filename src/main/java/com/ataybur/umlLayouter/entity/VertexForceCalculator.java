package com.ataybur.umlLayouter.entity;

import java.util.function.Function;

import com.ataybur.umlLayouter.util.ProjectConstants;

public class VertexForceCalculator {
    private Vertex v1;
    private Vertex v2;

    public VertexForceCalculator(Vertex v1, Vertex v2) {
	super();
	this.v1 = v1;
	this.v2 = v2;
    }

    public Force calculateAdjacentNodeForce(){
	return calculateCommonNodeForce(this::returnForceForAdjacentNode);
    }
    
    public Force calculateUnadjacentNodeForce(){
	return calculateCommonNodeForce(this::returnForceForUnadjacentNode);
      }
    
    private Force calculateCommonNodeForce(Function<Double, Double> calculateForce){
	Double xDistance = getDistance(Coordinate::getX);
	Double yDistance = getDistance(Coordinate::getY);
	Double xForce = calculateForce.apply(xDistance);
	Double yForce = calculateForce.apply(yDistance);
	return new Force(xForce, yForce);
    }
    
    private Double getDistance(Function<Coordinate, Double> getVector){
	Double vector1 = getVector.apply(v1.getCoordinate());
	Double vector2 = getVector.apply(v2.getCoordinate());
	return vector1- vector2;
    }
    
    private Double returnForceForAdjacentNode(Double distance) {
	if (distance == 0D) {
	    return 0D;
	}
	return ProjectConstants.C1 * Math.log10(Math.abs(distance) / ProjectConstants.C2);
    }
    
    private Double returnForceForUnadjacentNode(Double distance) {
	if (distance == 0D) {
	    return 0D;
	}
	return ProjectConstants.C3 / (distance * distance);
    }
}
