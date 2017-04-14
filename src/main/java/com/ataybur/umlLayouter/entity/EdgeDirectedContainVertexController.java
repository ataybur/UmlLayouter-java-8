package com.ataybur.umlLayouter.entity;

public class EdgeDirectedContainVertexController {
    protected Edge edge;
    protected Vertex vertex;

    public EdgeDirectedContainVertexController(Edge edge, Vertex vertex) {
	super();
	this.edge = edge;
	this.vertex = vertex;
    }

    public Boolean isContain() {
	return edge.getSecondVertexName().equalsIgnoreCase(vertex.getName());
    }
}
