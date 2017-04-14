package com.ataybur.umlLayouter.entity;

public class LineValidator implements Validator {
    private Line instance;

    public LineValidator(Line instance) {
	super();
	this.instance = instance;
    }

    @Override
    public boolean isValid() {
	boolean result;
	if (new CoordinateValidator(this.instance.getFirstPoint()).isNotValid()) {
	    result = false;
	} else if (new CoordinateValidator(this.instance.getSecondPoint()).isNotValid()) {
	    result = false;
	} else {
	    result = true;
	}
	return result;
    }

    @Override
    public boolean isNotValid() {
	return !isValid();
    }
}
