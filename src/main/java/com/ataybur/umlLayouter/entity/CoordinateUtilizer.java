package com.ataybur.umlLayouter.entity;

import java.util.Random;

import com.ataybur.umlLayouter.util.ProjectConstants;

public class CoordinateUtilizer {
    private Coordinate instance;
    
    public CoordinateUtilizer(Coordinate instance){
	this.instance = instance;
    }
    
    public static Coordinate createRandom(){
	Coordinate coordinate = new Coordinate();
 	Double newDouble = (new Random().nextDouble()) * ProjectConstants.RANDOM_CONSTANT;
 	coordinate.setX(newDouble);
 	newDouble = (new Random().nextDouble()) * ProjectConstants.RANDOM_CONSTANT;
 	coordinate.setY(newDouble);
 	return coordinate;
    }
}
