package com.ataybur.umlLayouter.service.gui.service;

import com.ataybur.umlLayouter.entity.Validator;
import com.ataybur.umlLayouter.entity.Vertex;

public class VertexValidator implements Validator{
    private Vertex instance;

    public VertexValidator(Vertex instance) {
	this.instance = instance;
    }

    public boolean isValid() {
	boolean result = true;
	if (this.instance == null) {
	    result = false;
	}
	return result;
    }

    @Override
    public boolean isNotValid() {
	return !isValid();
    }
}
