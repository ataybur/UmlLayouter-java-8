package com.ataybur.umlLayouter.entity;

import java.util.function.Supplier;

public class CoordinateValidator implements Validator {
    private Coordinate instance;

    public CoordinateValidator(Coordinate instance) {
	this.instance = instance;
    }

    @Override
    public boolean isValid() {
	return isNotValidPoint(instance::getX) && isNotValidPoint(instance::getY);
    }

    @Override
    public boolean isNotValid() {
	return !isValid();
    }

    private boolean isNotValidPoint(Supplier<Double> pointSupplier) {
	return !pointSupplier.get().equals(0D);
    }

}
