package com.ataybur.umlLayouter.service.gui.service;

import java.awt.Rectangle;

import com.ataybur.umlLayouter.entity.Coordinate;
import com.ataybur.umlLayouter.util.ProjectConstants;

public class CustomRectangle extends Rectangle{
    private static final long serialVersionUID = -1378006072564669853L;
    private static Integer unitSize = ProjectConstants.MATRIX_UNIT_SIZE / 10;
    
    public CustomRectangle(Coordinate coordinate){
	super(coordinate.getX().intValue(), coordinate.getY().intValue(), unitSize * 5, unitSize * 4);
    }

}
