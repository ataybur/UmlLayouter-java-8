/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.lastAdjuster.service;

import com.ataybur.umlLayouter.entity.Coordinate;
import com.ataybur.umlLayouter.entity.CustomGraph;
import com.ataybur.umlLayouter.entity.Edge;
import com.ataybur.umlLayouter.entity.Force;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.entity.VertexUtilizer;
import com.ataybur.umlLayouter.util.Utils;

/**
 *
 * @author atay
 */
abstract public class LastAdjusterService {
    protected CustomGraph fitGraphIntoScreen(CustomGraph graph) {
	for (Edge edge : graph.getEdgeList()) {
	    graph.setVertexList(Utils.fitEdgeIntoScreen(edge, graph.getVertexList()));
	}
	return graph;
    }

    protected CustomGraph adjustGraph(CustomGraph graph) {
	Double minAxis = graph.getVertexList().returnMinAxis();
	Double minOrdinate = graph.getVertexList().returnMinOrdinate();
	return shiftGraph(graph, minAxis, minOrdinate);
    }

    protected Coordinate getSumOfWidthAndHeightForBeyondPane(CustomGraph graph) {
	Double maxOrdinate = graph.getVertexList().returnMaxOrdinate();
	Double minOrdinate = graph.getVertexList().returnMinOrdinate();
	Double maxAxis = graph.getVertexList().returnMinAxis();
	Double minAxis = graph.getVertexList().returnMinAxis();

	Double ordinateDifference = maxOrdinate - minOrdinate;
	Double axisDifference = maxAxis - minAxis;
	Coordinate coordinateDifference = new Coordinate(axisDifference, ordinateDifference);
	return coordinateDifference;
    }

    private CustomGraph shiftGraph(CustomGraph graph, Double minAxis, Double minOrdinate) {
	Force force = new Force(-(minAxis - 1), -(minOrdinate - 1));
	for (Vertex vertex : graph.getVertexList()) {
	    vertex.setCoordinate(new VertexUtilizer(vertex).move(force).getCoordinate());
	}
	return graph;
    }

}
