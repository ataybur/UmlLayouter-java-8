package com.ataybur.umlLayouter.entity;

public class AdjacentNameExtractor {
    private Edge edge;
    private Vertex vertex;

    public AdjacentNameExtractor(Edge edge, Vertex vertex) {
	super();
	this.edge = edge;
	this.vertex = vertex;
    }

    public String extract() {
	if (edge.getFirstVertexName().equalsIgnoreCase(vertex.getName())) {
	    return edge.getSecondVertexName();
	} else if (edge.getSecondVertexName().equalsIgnoreCase(vertex.getName())) {
	    return edge.getFirstVertexName();
	} else {
	    return null;
	}
    }
}
