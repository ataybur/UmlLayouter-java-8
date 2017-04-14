package com.ataybur.umlLayouter.service.gui.service;

import java.util.ArrayList;
import java.util.List;

import com.ataybur.umlLayouter.entity.AdjacentNameExtractor;
import com.ataybur.umlLayouter.entity.CustomGraph;
import com.ataybur.umlLayouter.entity.Edge;
import com.ataybur.umlLayouter.entity.EdgeContainVertexConroller;
import com.ataybur.umlLayouter.entity.EdgeDirectedContainVertexController;
import com.ataybur.umlLayouter.entity.Force;
import com.ataybur.umlLayouter.entity.ForceCreater;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.entity.VertexUtilizer;

public class EdgeList extends ArrayList<Edge> {

    private static final long serialVersionUID = -3510145233102576141L;

    public List<String> returnAdjacentStringList(Vertex vertex) {
	String adjacentName;
	List<String> adjacentStringList = new ArrayList<String>();
	for (Edge edge : this) {
	    adjacentName = new AdjacentNameExtractor(edge, vertex).extract();
	    if (adjacentName != null) {
		adjacentStringList.add(adjacentName);
	    }
	}
	return adjacentStringList;
    }

    public EdgeList returnEdgeListWithoutThisVertex(Vertex vertex) {
	EdgeList newEdgeList = new EdgeList();
	for (Edge edge : this) {
	    if (!new EdgeContainVertexConroller(edge, vertex).isContain()) {
		newEdgeList.add(edge);
	    }
	}
	return newEdgeList;
    }

    public EdgeList returnEdgeListWithinThisVertex(Vertex vertex) {
	EdgeList newEdgeList = new EdgeList();
	for (Edge edge : this) {
	    if (new EdgeDirectedContainVertexController(edge, vertex).isContain()) {
		newEdgeList.add(edge);
	    }
	}
	return newEdgeList;
    }

    public EdgeList returnEdgeListByParentVertex(Vertex vertex) {
	EdgeList newEdgeList = new EdgeList();
	for (Edge edge : this) {
	    if (!edge.getIsAssociated() && new EdgeDirectedContainVertexController(edge, vertex).isContain()) {
		newEdgeList.add(edge);
	    }
	}
	return newEdgeList;
    }

    public EdgeList returnGeneralizedEdgeList() {
	EdgeList generalizedEdgeList = new EdgeList();
	for (Edge edge : this) {
	    if (!edge.getIsAssociated()) {
		generalizedEdgeList.add(edge);
	    }
	}
	return generalizedEdgeList;
    }

    public VertexList returnChildVertices(VertexList vertexList, String parent) {
	List<String> childVertexName = this.returnChildVertixNameList(parent);
	return vertexList.returnVertexListByNameList(childVertexName);
    }

    public List<String> returnChildVertixNameList(String parent) {
	EdgeList generalizedEdgeList = returnGeneralizedEdgeList();
	List<String> childVertexName = new ArrayList<String>();
	for (Edge edge : generalizedEdgeList) {
	    if (edge.getSecondVertexName().equalsIgnoreCase(parent)) {
		childVertexName.add(edge.getFirstVertexName());
	    }
	}
	return childVertexName;
    }

    public List<String> returnParentList() {
	EdgeList generalizedEdgeList = returnGeneralizedEdgeList();
	List<String> parentList = new ArrayList<String>();
	for (Edge edge : generalizedEdgeList) {
	    if (!parentList.contains(edge.getSecondVertexName())) {
		parentList.add(edge.getSecondVertexName());
	    }
	}
	return parentList;
    }

    public List<String> returnParentList(String childName) {
	EdgeList generalizedEdgeList = returnGeneralizedEdgeList();
	List<String> parentList = new ArrayList<String>();
	for (Edge edge : generalizedEdgeList) {
	    if (!parentList.contains(edge.getSecondVertexName()) && edge.getFirstVertexName().equalsIgnoreCase(childName)) {
		parentList.add(edge.getSecondVertexName());
	    }
	}
	return parentList;
    }

    public void setCoordinateToChild(VertexList vertexList, String parent) {
	VertexList childVertexList = this.returnChildVertices(vertexList, parent);
	Vertex parentVertex = vertexList.getVertexByName(parent);
	CustomGraph graph = new CustomGraph();
	VertexList childrenAndParentVertexList = new VertexList(childVertexList);
	childrenAndParentVertexList.add(parentVertex);
	graph.setVertexList(vertexList);
	graph.setEdgeList(returnEdgeListByParentVertex(parentVertex));
	VertexList newVertexList = graph.doAlgorithm();
	parentVertex = vertexList.getVertexByName(parent);
	Vertex newParentVertex = newVertexList.getVertexByName(parent);
	Force force = new ForceCreater(parentVertex, newParentVertex).create();
	Double ordinate = newParentVertex.getCoordinate().getY();
	boolean isFirst = true;
	for (Vertex vertex : childrenAndParentVertexList) {
	    vertex.setCoordinate(new VertexUtilizer(vertex).moveForAlgorithm(force).getCoordinate());
	    vertexList.updateVertex(vertex);
	    if (!vertex.getName().equalsIgnoreCase(parent)) {
		if (isFirst) {
		    isFirst = false;
		    ordinate = vertex.getCoordinate().getY();
		} else {
		    vertex.getCoordinate().setY(ordinate);
		}
	    }
	}
    }
    
    public boolean isListContainEdge(Edge edge) {
	return this //
		.stream() //
		.filter((edgeTemp) -> edgeTemp.equals(edge)) //
		.findFirst() //
		.isPresent();
    }

}
