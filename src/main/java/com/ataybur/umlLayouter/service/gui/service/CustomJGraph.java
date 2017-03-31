package com.ataybur.umlLayouter.service.gui.service;

import org.jgraph.JGraph;

public class CustomJGraph extends JGraph {

    private static final long serialVersionUID = 8459129964149391320L;
    private CustomModelAdapter modelAdapter;
    public CustomJGraph() {
	super(new CustomModelAdapter());
	modelAdapter = (CustomModelAdapter) this.getModel();
    }

    @Override
    public CustomModelAdapter getModel() {
        return modelAdapter;
    }
}
