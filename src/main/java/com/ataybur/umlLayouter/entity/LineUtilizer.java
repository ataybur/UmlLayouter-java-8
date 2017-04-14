package com.ataybur.umlLayouter.entity;

public class LineUtilizer {
    private Line instance;
    
    public LineUtilizer(Line instance){
	this.instance = instance;
    }
    
    public  Double calculateD(Line secondLine) {
	Double AX = this.instance.getFirstPoint().getX();
	Double AY = this.instance.getFirstPoint().getY();
	Double BX = this.instance.getSecondPoint().getX();
	Double BY = this.instance.getSecondPoint().getY();

	Double CX = secondLine.getFirstPoint().getX();
	Double CY = secondLine.getFirstPoint().getY();
	Double DX = secondLine.getSecondPoint().getX();
	Double DY = secondLine.getSecondPoint().getY();

	Double first = ((DY - CY) * (BX - AX)) - ((BY - AY) * (DX - CX));
	if (first.equals(0D)) {
	    return null;
	}
	Double second = ((DY - CY) * (CX - AX)) - ((CY - AY) * (DX - CX));
	return second / first;
    }

    public  Double calculateIntersectionX(Line secondLine) {
	// Double AX = this.instance.getFirstPoint().getX();
	// Double AY = this.instance.getFirstPoint().getY();
	// Double BX = this.instance.getSecondPoint().getX();
	// Double BY = this.instance.getSecondPoint().getY();
	//
	// return AX + (BX - AX) * d;
	Double a1 = this.instance.getSlope();
	Double a2 = secondLine.getSlope();
	Double b1 = this.instance.getIntercept();
	Double b2 = secondLine.getIntercept();
	return (b2 - b1) / (a1 - a2);
    }

    public  Double calculateIntersectionY(Double x) {
	return 1D;
	// return this.instance.getSlope() * x + this.instance.getIntercept();
    }

    public  Boolean areLinesCollide(Line secondLine) {
	Boolean result = false;
	if (this.instance.getSlope().equals(secondLine.getSlope()) || this.instance.getSlope().equals(-secondLine.getSlope())) {
	    result = areLinesHasSamePoints(secondLine);
	}
	return result;
    }

    public  Boolean areLinesHasSamePoints(Line secondLine) {
	Boolean result = false;
	if (this.instance.getFirstPoint().equals(secondLine.getFirstPoint())) {
	    result = true;
	} else if (this.instance.getSecondPoint().equals(secondLine.getSecondPoint())) {
	    result = true;
	} else if (this.instance.getFirstPoint().equals(secondLine.getSecondPoint())) {
	    result = true;
	} else if (this.instance.getSecondPoint().equals(secondLine.getFirstPoint())) {
	    result = true;
	}
	return result;
    }
    

    public Boolean isPointOn(Double x, Double y) {
	Coordinate firstPoint = this.instance.getFirstPoint();
	Coordinate secondPoint = this.instance.getSecondPoint();
	Boolean forX = isPointBetweenTwoPoint(x, firstPoint.getX(), secondPoint.getX());
	Boolean forY = isPointBetweenTwoPoint(y, firstPoint.getY(), secondPoint.getY());
	return forX && forY;
    }
    
    private Boolean isPointBetweenTwoPoint(Double point, Double linePoint1, Double linePoint2) {
	if (linePoint1 < linePoint2) {
	    return (linePoint1 < point) && (linePoint2 > point);
	} else {
	    return (linePoint2 < point) && (linePoint1 > point);
	}
    }
}
