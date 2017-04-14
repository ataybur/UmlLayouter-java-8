package com.ataybur.umlLayouter.entity;

import java.util.function.Function;

public class ForceCreater {
    private Vertex v1;
    private Vertex v2;

    public ForceCreater(Vertex v1, Vertex v2) {
	super();
	this.v1 = v1;
	this.v2 = v2;
    }
    
    public Force create(){
	Double xDistance = getDistance(Coordinate::getX);
	Double yDistance = getDistance(Coordinate::getY);
	return new Force(xDistance, yDistance);
    }
    
    private Double getDistance(Function<Coordinate, Double> getVector){
	Double vector1 = getVector.apply(v1.getCoordinate());
	Double vector2 = getVector.apply(v2.getCoordinate());
	return vector1- vector2;
    }
}
