/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.generalizationAdjuster.service;

import java.util.List;

import com.ataybur.umlLayouter.entity.CustomGraph;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.service.gui.service.EdgeList;
import com.ataybur.umlLayouter.service.gui.service.VertexList;
import com.ataybur.umlLayouter.util.ProjectConstants;

/**
 *
 * @author atay
 */
abstract public class GeneralizationAdjusterService {

    protected void doLayoutForGeneralizedEdges(CustomGraph graph) {
	doLayoutForGeneralizedEdges(graph.getEdgeList(), graph.getVertexList());
	List<String> parentList = graph.getEdgeList().returnParentList();
	for (String parent : parentList) {
	    doCenteredParent(graph, parent);
	}
    }

    private void doLayoutForGeneralizedEdges(EdgeList edgeList, VertexList vertexList) {
	List<String> parentList = edgeList.returnParentList();
	for (String parent : parentList) {
	    edgeList.setCoordinateToChild(vertexList, parent);
	}
    }

    private void doCenteredParent(CustomGraph graph, String parent) {
	VertexList childrenList = graph.getEdgeList().returnChildVertices(graph.getVertexList(), parent);
	Vertex parentVertex = graph.getVertexList().getVertexByName(parent);
	Double unitSize = new Double(ProjectConstants.MATRIX_UNIT_SIZE);

	Double ordinate = parentVertex.getCoordinate().getY();
	boolean isFirst = true;

	Double tempOrdinate = childrenList.returnMinOrdinate();
	ordinate = tempOrdinate;
	// parentVertex.getCoordinate().setX(newX);

	// parentVertex.setCoordinate(algorithm.moveTheVertex(parentVertex,
	// force).getCoordinate());
	for (Vertex vertex : childrenList) {
	    // vertex.setCoordinate(algorithm.moveTheVertex(vertex,
	    // force).getCoordinate());
	    if (isFirst) {
		isFirst = false;
		ordinate = vertex.getCoordinate().getY();
		if (parentVertex.getCoordinate().getY() > ordinate) {
		    tempOrdinate = parentVertex.getCoordinate().getY() - ordinate;
		    ordinate += 2 * tempOrdinate;
		}
	    }
	    vertex.getCoordinate().setY(ordinate);
	}
	Double axisDifference = returnAxisDifference(childrenList);
	System.out.println("unitSize: " + unitSize);
	System.out.println("axisDifference: " + axisDifference);
	if (axisDifference < unitSize) {
	    for (int i = 0; i < childrenList.size(); i++) {
		childrenList.get(i).getCoordinate().setX(childrenList.get(i).getCoordinate().getX() + (unitSize * i));
	    }
	}
	Double newX = returnCenterAxis(childrenList);
	Double forceX = parentVertex.getCoordinate().getX() - newX;
	for (Vertex vertex : childrenList) {
	    vertex.getCoordinate().setX(vertex.getCoordinate().getX() + forceX);
	}
	// parentVertex.getCoordinate().setY(-parentVertex.getCoordinate().getY());
    }

    private Double returnAxisDifference(List<Vertex> childrenList) {
	Double minX = Double.MAX_VALUE;
	Double maxX = Double.MIN_VALUE;
	for (Vertex vertex : childrenList) {
	    if (vertex.getCoordinate().getX() < minX) {
		minX = vertex.getCoordinate().getX();
	    }
	    if (vertex.getCoordinate().getX() > maxX) {
		maxX = vertex.getCoordinate().getX();
	    }
	}
	return Math.abs(minX - maxX);
    }

    private Double returnCenterAxis(List<Vertex> childrenList) {
	Double minX = Double.MAX_VALUE;
	Double maxX = Double.MIN_VALUE;
	for (Vertex vertex : childrenList) {
	    if (vertex.getCoordinate().getX() < minX) {
		minX = vertex.getCoordinate().getX();
	    }
	    if (vertex.getCoordinate().getX() > maxX) {
		maxX = vertex.getCoordinate().getX();
	    }
	}
	Double newX = (minX + maxX) / 2;
	return newX;
    }
}
