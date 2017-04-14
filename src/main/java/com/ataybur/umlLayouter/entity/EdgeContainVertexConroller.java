package com.ataybur.umlLayouter.entity;

public class EdgeContainVertexConroller extends EdgeDirectedContainVertexController{

    public EdgeContainVertexConroller(Edge edge, Vertex vertex) {
	super(edge, vertex);
    }
    
    public Boolean isContain() {
	return edge.getFirstVertexName().equalsIgnoreCase(vertex.getName()) || super.isContain();
    }

}
