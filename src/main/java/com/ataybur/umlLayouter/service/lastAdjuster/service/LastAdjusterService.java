/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.lastAdjuster.service;

import com.ataybur.umlLayouter.entity.Coordinate;
import com.ataybur.umlLayouter.entity.Edge;
import com.ataybur.umlLayouter.entity.Force;
import com.ataybur.umlLayouter.entity.Graph;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.util.Utils;

/**
 *
 * @author atay
 */
abstract public class LastAdjusterService {
    protected Graph fitGraphIntoScreen(Graph graph, Integer screenSize) {
	for (Edge edge : graph.getEdgeList()) {
	    graph.setVertexList(Utils.fitEdgeIntoScreen(edge, graph.getVertexList(), screenSize));
	}
	return graph;
    }

    protected Graph adjustGraph(Graph graph) {
	Double minAxis = Utils.returnMinAxis(graph.getVertexList());
	Double minOrdinate = Utils.returnMinOrdinate(graph.getVertexList());
	return shiftGraph(graph, minAxis, minOrdinate);
    }

    protected Coordinate getSumOfWidthAndHeightForBeyondPane(Graph graph) {
	Double maxOrdinate = Utils.returnMaxOrdinate(graph.getVertexList());
	Double minOrdinate = Utils.returnMinOrdinate(graph.getVertexList());
	Double maxAxis = Utils.returnMinAxis(graph.getVertexList());
	Double minAxis = Utils.returnMinAxis(graph.getVertexList());

	Double ordinateDifference = maxOrdinate - minOrdinate;
	Double axisDifference = maxAxis - minAxis;
	Coordinate coordinateDifference = new Coordinate(axisDifference, ordinateDifference);
	return coordinateDifference;
    }

    private Graph shiftGraph(Graph graph, Double minAxis, Double minOrdinate) {
	Force force = new Force(-(minAxis - 1), -(minOrdinate - 1));
	for (Vertex vertex : graph.getVertexList()) {
	    vertex.setCoordinate(Utils.moveTheVertex(vertex, force).getCoordinate());
	}
	return graph;
    }

}
