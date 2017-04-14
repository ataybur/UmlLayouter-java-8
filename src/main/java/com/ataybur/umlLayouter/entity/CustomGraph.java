/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.entity;

import java.util.List;

import com.ataybur.umlLayouter.service.gui.service.EdgeList;
import com.ataybur.umlLayouter.service.gui.service.VertexList;
import com.ataybur.umlLayouter.util.ProjectConstants;

/**
 *
 * @author atay
 */
public class CustomGraph {

    private VertexList vertexList;
    private EdgeList edgeList;

    public CustomGraph() {
	vertexList = new VertexList();
	edgeList = new EdgeList();
    }

    public CustomGraph(CustomGraph graph) {
	ProjectConstants.VERTEX_COUNTER += 1;
	vertexList = new VertexList();
	edgeList = new EdgeList();

	for (Vertex vertex : graph.getVertexList()) {
	    vertexList.add(new Vertex(vertex));
	}

	for (Edge edge : graph.getEdgeList()) {
	    edgeList.add(edge);
	}
    }

    public CustomGraph(VertexList vertexList) {
	this.vertexList = vertexList;
	this.edgeList = new EdgeList();
    }

    public VertexList getVertexList() {
	return vertexList;
    }

    public void setVertexList(VertexList vertexList) {
	this.vertexList = vertexList;
    }

    public EdgeList getEdgeList() {
	return edgeList;
    }

    public void setEdgeList(EdgeList edgeList) {
	this.edgeList = edgeList;
    }

    public VertexList returnAdjacentList(Vertex vertex) {
	return returnAdjacentList(edgeList, vertexList, vertex);
    }

    public VertexList returnUnadjacentList(Vertex vertex) {
	return returnUnadjacentList(edgeList, vertexList, vertex);
    }

    private VertexList returnAdjacentList(EdgeList edgeList, VertexList vertexList, Vertex vertex) {
	List<String> adjacentStringList = edgeList.returnAdjacentStringList(vertex);
	return vertexList.returnVertexListByNameList(adjacentStringList);
    }

    private VertexList returnUnadjacentList(EdgeList edgeList, VertexList vertexList, Vertex vertex) {
	List<String> adjacentStringList = edgeList.returnAdjacentStringList(vertex);
	List<String> unadjacentStringList = vertexList.returnUnadjacentStringList(adjacentStringList);
	return vertexList.returnVertexListByNameList(unadjacentStringList);
    }
    
    public VertexList doAlgorithm() {
	Force forceOnVertex;
	for (int i = 0; i < ProjectConstants.M; i++) {
	    for (Vertex vertex : vertexList) {
		forceOnVertex = calculateForceOnVertex(vertex);
		new VertexUtilizer(vertex).moveForAlgorithm(forceOnVertex);
	    }
	}
	return vertexList;
    }
    

    private Force calculateForceOnVertex(Vertex vertex) {
	VertexList adjacentVertexList = returnAdjacentList(vertex);
	VertexList unadjacentVertexList = returnUnadjacentList(vertex);
	Force forceForAdjacentNodeList = calculateForceForAdjacentNodeList(vertex, adjacentVertexList);
	Force forceForUnadjacentNodeList = calculateForceForUnadjacentNodeList(vertex, unadjacentVertexList);
	return new ForceCalculator(forceForAdjacentNodeList, forceForUnadjacentNodeList).sum();
    }
    
    private Force calculateForceForAdjacentNodeList(Vertex vertex, VertexList adjacentVertexList) {
	Force forceForAdjacentNodeList = new Force();
	Force tempForce = new Force();
	for (Vertex adjacentVertex : adjacentVertexList) {
	    tempForce = returnForceForAdjacentNode(vertex, adjacentVertex);
	    forceForAdjacentNodeList = new ForceCalculator(forceForAdjacentNodeList, tempForce).sum();
	}

	return forceForAdjacentNodeList;
    }

    private Force calculateForceForUnadjacentNodeList(Vertex vertex, List<Vertex> unadjacentNodeList) {
	Force forceForUnadjacentNodeList = new Force();
	Force tempForce = new Force();
	for (Vertex unadjacentVertex : unadjacentNodeList) {
	    tempForce = returnForceForUnadjacentNode(vertex, unadjacentVertex);
	    forceForUnadjacentNodeList = new ForceCalculator(forceForUnadjacentNodeList, tempForce).sum();
	}

	return forceForUnadjacentNodeList;
    }

    private Force returnForceForAdjacentNode(Vertex v1, Vertex v2) {
	return new VertexForceCalculator(v1, v2).calculateAdjacentNodeForce();
    }

    private Force returnForceForUnadjacentNode(Vertex v1, Vertex v2) {
	return new VertexForceCalculator(v1, v2).calculateUnadjacentNodeForce();
    }
    
    public void printGraph() {

 	String vertexName;
 	System.out.println("List<Vertex> vertexList = new ArrayList<Vertex>();");
 	for (Vertex vertex : vertexList) {
 	    vertexName = vertex.getName();
 	    System.out.println("Coordinate " + vertexName + " = new Coordinate(" + vertex.getCoordinate().getX() + "," + vertex.getCoordinate().getY() + ");");
 	    System.out.println("Vertex vertex" + vertexName + " = new Vertex(\"" + vertexName + "\");");
 	    System.out.println("vertex" + vertexName + ".setCoordinate(" + vertexName + ");");
 	    System.out.println("vertexList.add(vertex" + vertexName + ");");
 	}
 	String first, second;
 	System.out.println("List<Edge> edgeList = new ArrayList<Edge>();");
 	for (Edge edge : edgeList) {
 	    first = edge.getFirstVertexName();
 	    second = edge.getSecondVertexName();
 	    System.out.println("Edge edge" + second + first + " = new Edge(vertex" + first + ", vertex" + second + ", " + edge.getIsAssociated() + ");");
 	    System.out.println("edgeList.add(edge" + second + first + ");");
 	}
     }
     public Boolean isGraphContainVertex(Vertex vertex) {
 	return isGraphContainVertexName(vertex.getName());
     }

     public Boolean isGraphContainVertexName(String vertexName) {
 	return vertexList.isVertexListContainVertexName(vertexName);
     }
}
