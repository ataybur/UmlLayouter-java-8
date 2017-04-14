package com.ataybur.umlLayouter.service.gui.service;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import com.ataybur.umlLayouter.entity.Edge;

public class CustomGuiGraph extends ListenableDirectedGraph<String, DefaultEdge>{

    private static final long serialVersionUID = 1852738423920564575L;

    public CustomGuiGraph(){
	this(DefaultEdge.class);
    }
    
    public CustomGuiGraph(Class<? extends DefaultEdge> edgeClass) {
	super(edgeClass);
    }
    
    public DefaultEdge addEdge(Edge edge) {
        return super.addEdge(edge.getFirstVertexName(), edge.getSecondVertexName());
    }

}
