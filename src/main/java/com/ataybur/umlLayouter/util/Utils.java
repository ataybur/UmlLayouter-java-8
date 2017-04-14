package com.ataybur.umlLayouter.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ataybur.umlLayouter.entity.CustomGraph;
import com.ataybur.umlLayouter.entity.Edge;
import com.ataybur.umlLayouter.entity.Force;
import com.ataybur.umlLayouter.entity.Line;
import com.ataybur.umlLayouter.entity.LineUtilizer;
import com.ataybur.umlLayouter.entity.LineValidator;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.entity.VertexUtilizer;
import com.ataybur.umlLayouter.service.gui.service.VertexList;

/**
 *
 * @author atay
 */
public class Utils {

    public static Boolean areThereConflictedEdges(Edge firstEdge, Edge secondEdge, CustomGraph graph) {
	VertexList vertexList = graph.getVertexList();
	Line firstLine = vertexList.returnLine(firstEdge);
	Line secondLine = vertexList.returnLine(secondEdge);
	Boolean result = areThereConflictedLines(firstLine, secondLine);
	return result;
    }

    /*
     * ?
     * */
    public static Boolean areLinesValid(Line... lineArg){
	return !Stream.of(lineArg) //
		.map(LineValidator::new) //
		.map(LineValidator::isNotValid) //
		.collect(Collectors.toList()) //
		.contains(Boolean.FALSE);
    }
    
    public static Boolean areThereConflictedLines(Line firstLine, Line secondLine) {
	if (new LineValidator(firstLine).isNotValid() || new LineValidator(secondLine).isNotValid()) {
	    /*
	     * TODO
	     * 
	     * Valid olmayan ayrıtlar daha sonra valid hale geldiklerinde
	     * kesişen koordinatlara sahip olabiliyor.
	     * 
	     * Bunu önlemek için ne yapmalı?
	     * 
	     */
	    return false;
	}
	LineUtilizer lineUtilizer = new LineUtilizer(firstLine);
	if (lineUtilizer.areLinesCollide(secondLine)) {
	    return true;
	} else if (lineUtilizer.areLinesHasSamePoints(secondLine)) {
	    return false;
	}

	Double d = lineUtilizer.calculateD(secondLine);

	if (d == null) {
	    return true;
	} else {

	    Double x = lineUtilizer.calculateIntersectionX(secondLine);
	    Double y = lineUtilizer.calculateIntersectionY(x);

	    Boolean result = lineUtilizer.isPointOn(x, y);
	    // Double yTemp = firstLine.getSlope() * x +
	    // firstLine.getIntercept();
	    // System.out.println("y: " + y + ", yTemp: " + yTemp +
	    // ",firstLine.getSlope(): " + firstLine.getSlope() + ",
	    // firstLine.getIntercept(): " + firstLine.getIntercept());
	    // System.out.println("areThereConflictedLines Ends " +
	    // y.equals(yTemp));
	    return result;
	}
    }

    public static VertexList makeEdgeShort(Edge edge, VertexList vertexList, Double xDistance, Double yDistance) {
	Vertex firstVertex = vertexList.getVertexByName(edge.getFirstVertexName());
	Vertex secondVertex = vertexList.getVertexByName(edge.getSecondVertexName());
	Line line = new Line(firstVertex, secondVertex);
	Vertex vertexTemp = new VertexUtilizer(firstVertex).returnOneDegree(secondVertex);
	if (vertexTemp == null) {
	    return vertexList;
	}
	vertexTemp = makeVertexShift(line, vertexTemp, xDistance, yDistance);
	return vertexList.updateVertex(vertexTemp);
    }

    private static Vertex makeVertexShift(Line line, Vertex vertex, Double xDistance, Double yDistance) {
	Vertex result = makeVertexShiftByAxis(line, vertex, xDistance);
	result = makeVertexShiftByOrdinate(line, result, yDistance);
	return result;
    }

    private static Vertex makeVertexShiftByAxis(Line line, Vertex vertex, Double xDistance) {
	Double x = vertex.getCoordinate().getX();
	Double y = vertex.getCoordinate().getY();
	Double newX = x + xDistance;
	Double slope = 0D;
	if (!Double.isInfinite(line.getSlope())) {
	    slope = line.getSlope();
	}
	Double newY = y - Math.abs((xDistance * slope));
	vertex.getCoordinate().setX(newX);
	vertex.getCoordinate().setY(newY);
	return vertex;
    }

    private static Vertex makeVertexShiftByOrdinate(Line line, Vertex vertex, Double yDistance) {
	Double x = vertex.getCoordinate().getX();
	Double y = vertex.getCoordinate().getY();
	Double slope = 1D;
	if (!Double.isInfinite(line.getSlope())) {
	    slope = line.getSlope();
	}
	Double newX = x + (yDistance / -(slope));
	Double newY = y - Math.abs(yDistance);
	vertex.getCoordinate().setX(newX);
	vertex.getCoordinate().setY(newY);
	return vertex;
    }

    public static VertexList fitEdgeIntoScreen(Edge edge, VertexList vertexList) {
	Force distance = vertexList.returnDistanceForEdge(edge);
	Double xDistance = distance.getX();
	Double yDistance = distance.getY();
	Double newXDistance = shiftableDistance(xDistance);
	Double newYDistance = shiftableDistance(yDistance);
	return makeEdgeShort(edge, vertexList, newXDistance, newYDistance);
    }

    private static Double shiftableDistance(Double distance) {
	Double doubleMatrixSize = new Double((ProjectConstants.DEFAULT_SIZE.height / 8));
	Double absDistance = Math.abs(distance);
	if (absDistance <= doubleMatrixSize) {
	    return 0D;
	} else {
	    return doubleMatrixSize - absDistance;
	}
    }

}
