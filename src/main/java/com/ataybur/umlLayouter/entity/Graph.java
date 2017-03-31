/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.entity;

import java.util.ArrayList;
import java.util.List;

import com.ataybur.umlLayouter.util.ProjectConstants;

/**
 *
 * @author atay
 */
public class Graph {

    private List<Vertex> vertexList;
    private List<Edge> edgeList;

    public Graph() {
	vertexList = new ArrayList<Vertex>();
	edgeList = new ArrayList<Edge>();
    }

    public Graph(Graph graph) {
	ProjectConstants.VERTEX_COUNTER += 1;
	vertexList = new ArrayList<Vertex>();
	edgeList = new ArrayList<Edge>();

	for (Vertex vertex : graph.getVertexList()) {
	    vertexList.add(new Vertex(vertex));
	}

	for (Edge edge : graph.getEdgeList()) {
	    edgeList.add(edge);
	}
    }

    public Graph(List<Vertex> vertexList) {
	this.vertexList = vertexList;
	this.edgeList = new ArrayList<Edge>();
    }

    public List<Vertex> getVertexList() {
	return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
	this.vertexList = vertexList;
    }

    public List<Edge> getEdgeList() {
	return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
	this.edgeList = edgeList;
    }
}
