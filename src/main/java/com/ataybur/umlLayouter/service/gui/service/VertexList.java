package com.ataybur.umlLayouter.service.gui.service;

import java.util.ArrayList;
import java.util.List;

import com.ataybur.umlLayouter.entity.Edge;
import com.ataybur.umlLayouter.entity.Force;
import com.ataybur.umlLayouter.entity.Line;
import com.ataybur.umlLayouter.entity.Vertex;

public class VertexList extends ArrayList<Vertex> {

    private static final long serialVersionUID = -7239305221975000605L;

    public VertexList(VertexList vertexList) {
	super(vertexList);
    }
    
    public VertexList() {
	super();
    }
    
    public VertexList returnVertexListByNameList(List<String> nameList) {
	VertexList adjacentVertexList = new VertexList();
	Vertex adjacentVertex;
	for (String adjacentVertexName : nameList) {
	    adjacentVertex = getVertexByName(adjacentVertexName);
	    if (new VertexValidator(adjacentVertex).isValid()) {
		adjacentVertexList.add(adjacentVertex);
	    }
	}
	return adjacentVertexList;
    }

    public Vertex getVertexByName(String name) {
	for (Vertex vertex : this) {
	    if (vertex.getName().equalsIgnoreCase(name)) {
		return vertex;
	    }
	}
	return null;
    }

    public VertexList updateVertex(Vertex vertex) {
	this //
		.getVertexByName(vertex.getName()) //
		.setCoordinate(vertex.getCoordinate());
	return this;
    }

    public List<String> returnUnadjacentStringList(List<String> adjacentStringList) {
	List<String> unadjacentStringList = new ArrayList<String>();
	for (Vertex vertex : this) {
	    if (!adjacentStringList.contains(vertex.getName())) {
		unadjacentStringList.add(vertex.getName());
	    }
	}
	return unadjacentStringList;
    }

    public Double returnMinOrdinate() {
	Double minOrdinate = Double.MAX_VALUE;
	for (Vertex vertex : this) {
	    if (minOrdinate > vertex.getCoordinate().getY()) {
		minOrdinate = vertex.getCoordinate().getY();
	    }
	}
	return minOrdinate;
    }

    public Double returnMaxOrdinate() {
	Double maxOrdinate = Double.MIN_VALUE;
	for (Vertex vertex : this) {
	    if (maxOrdinate < vertex.getCoordinate().getY()) {
		maxOrdinate = vertex.getCoordinate().getY();
	    }
	}
	return maxOrdinate;
    }

    public Double returnMinAxis() {
	Double minAxis = Double.MAX_VALUE;
	for (Vertex vertex : this) {
	    if (minAxis > vertex.getCoordinate().getX()) {
		minAxis = vertex.getCoordinate().getX();
	    }
	}
	return minAxis;
    }

    public Double returnMaxAxis() {
	Double maxAxis = Double.MIN_VALUE;
	for (Vertex vertex : this) {
	    if (maxAxis < vertex.getCoordinate().getX()) {
		maxAxis = vertex.getCoordinate().getX();
	    }
	}
	return maxAxis;
    }

    public Boolean isVertexListContainVertex(Vertex vertex) {
	return isVertexListContainVertexName(vertex.getName());
    }

    public Boolean isVertexListContainVertexName(String vertexName) {
	for (Vertex vertex : this) {
	    if (vertex.getName().equalsIgnoreCase(vertexName)) {
		return true;
	    }
	}
	return false;
    }

    public Force returnDistanceForEdge(Edge edge) {
	Vertex firstVertex = this.getVertexByName(edge.getFirstVertexName());
	Vertex secondVertex = this.getVertexByName(edge.getSecondVertexName());
	Double xDistance = firstVertex.getCoordinate().getX() - secondVertex.getCoordinate().getX();
	Double yDistance = firstVertex.getCoordinate().getY() - secondVertex.getCoordinate().getY();
	return new Force(xDistance, yDistance);
    }

    public VertexList removeUnadjacent(Vertex vertex) {
	return removeUnadjacent(vertex.getName());
    }

    public VertexList removeUnadjacent(String vertexName) {
	int vertexIndex = 0;
	int i = 0;
	for (Vertex vertex : this) {
	    if (vertex.getName().equalsIgnoreCase(vertexName)) {
		vertexIndex = i;
		break;
	    }
	    i++;
	}
	this.remove(vertexIndex);
	return this;
    }

    public Line returnLine(Edge edge) {
	Vertex firstVertex = getVertexByName(edge.getFirstVertexName());
	Vertex secondVertex = getVertexByName(edge.getSecondVertexName());
	return new Line(firstVertex.getCoordinate(), secondVertex.getCoordinate());
    }

}
