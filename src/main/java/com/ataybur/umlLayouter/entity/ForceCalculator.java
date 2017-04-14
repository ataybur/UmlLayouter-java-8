package com.ataybur.umlLayouter.entity;

public class ForceCalculator {
    private Force f1;
    private Force f2;

    public ForceCalculator(Force f1, Force f2) {
	super();
	this.f1 = f1;
	this.f2 = f2;
    }
    
    public Force sum() {
	Double xForce = f1.getX() + f2.getX();
	Double yForce = f1.getY() + f2.getY();
	return new Force(xForce, yForce);
    }
}
