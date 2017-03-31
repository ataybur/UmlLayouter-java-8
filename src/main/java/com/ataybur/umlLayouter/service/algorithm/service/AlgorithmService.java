/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.algorithm.service;

import java.util.List;

import com.ataybur.umlLayouter.entity.Force;
import com.ataybur.umlLayouter.entity.Graph;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.util.ProjectConstants;
import com.ataybur.umlLayouter.util.Utils;

/**
 *
 * @author atay
 */
abstract public class AlgorithmService {

    protected List<Vertex> doAlgorithm(Graph graph) {
	Force forceOnVertex;
	for (int i = 0; i < ProjectConstants.M; i++) {
	    for (Vertex vertex : graph.getVertexList()) {
		forceOnVertex = calculateForceOnVertex(graph, vertex);
		moveTheVertexForAlgorithm(vertex, forceOnVertex);
	    }
	}
	return graph.getVertexList();
    }

    private Force calculateForceOnVertex(Graph graph, Vertex vertex) {
	List<Vertex> adjacentVertexList = Utils.returnAdjacentList(graph, vertex);
	List<Vertex> unadjacentVertexList = Utils.returnUnadjacentList(graph, vertex);
	Force forceForAdjacentNodeList = calculateForceForAdjacentNodeList(vertex, adjacentVertexList);
	Force forceForUnadjacentNodeList = calculateForceForUnadjacentNodeList(vertex, unadjacentVertexList);
	return Utils.sumTwoForces(forceForAdjacentNodeList, forceForUnadjacentNodeList);
    }

    protected Vertex moveTheVertexForAlgorithm(Vertex vertex, Force force) {
	Double xForceDistance = force.getX();
	Double yForceDistance = force.getY();
	Double xCoordinate = vertex.getCoordinate().getX() + (ProjectConstants.C4 * xForceDistance);
	Double yCoordinate = vertex.getCoordinate().getY() + (ProjectConstants.C4 * yForceDistance);
	vertex.getCoordinate().setX(xCoordinate);
	vertex.getCoordinate().setY(yCoordinate);
	return vertex;
    }

    private Force calculateForceForAdjacentNodeList(Vertex vertex, List<Vertex> adjacentVertexList) {
	Force forceForAdjacentNodeList = new Force();
	Force tempForce = new Force();
	for (Vertex adjacentVertex : adjacentVertexList) {
	    tempForce = returnForceForAdjacentNode(vertex, adjacentVertex);
	    forceForAdjacentNodeList = Utils.sumTwoForces(forceForAdjacentNodeList, tempForce);
	}

	return forceForAdjacentNodeList;
    }

    private Force calculateForceForUnadjacentNodeList(Vertex vertex, List<Vertex> unadjacentNodeList) {
	Force forceForUnadjacentNodeList = new Force();
	Force tempForce = new Force();
	for (Vertex unadjacentVertex : unadjacentNodeList) {
	    tempForce = returnForceForUnadjacentNode(vertex, unadjacentVertex);
	    forceForUnadjacentNodeList = Utils.sumTwoForces(forceForUnadjacentNodeList, tempForce);
	}

	return forceForUnadjacentNodeList;
    }

    private Force returnForceForAdjacentNode(Vertex v1, Vertex v2) {
	Double xDistance = v1.getCoordinate().getX() - v2.getCoordinate().getX();
	Double yDistance = v1.getCoordinate().getY() - v2.getCoordinate().getY();
	Double xForce = Utils.returnForceForAdjacentNode(ProjectConstants.C1, xDistance, ProjectConstants.C2);
	Double yForce = Utils.returnForceForAdjacentNode(ProjectConstants.C1, yDistance, ProjectConstants.C2);
	return new Force(xForce, yForce);
    }

    private Force returnForceForUnadjacentNode(Vertex v1, Vertex v2) {
	Double xDistance = v1.getCoordinate().getX() - v2.getCoordinate().getX();
	Double yDistance = v1.getCoordinate().getY() - v2.getCoordinate().getY();
	Double xForce = Utils.returnForceForUnadjacentNode(ProjectConstants.C3, xDistance);
	Double yForce = Utils.returnForceForUnadjacentNode(ProjectConstants.C3, yDistance);
	return new Force(xForce, yForce);
    }
}
