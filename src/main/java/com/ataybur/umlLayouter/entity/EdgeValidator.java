package com.ataybur.umlLayouter.entity;

import com.ataybur.umlLayouter.service.gui.service.VertexList;

public class EdgeValidator implements Validator {
    private Edge instance;
    private VertexList vertexList;

    public EdgeValidator(Edge instance, VertexList vertexList) {
	super();
	this.instance = instance;
	this.vertexList = vertexList;
    }

    @Override
    public boolean isValid() {
	Line line = vertexList.returnLine(this.instance);
	return new LineValidator(line).isValid();
    }

    @Override
    public boolean isNotValid() {
	return !isValid();
    }

}
