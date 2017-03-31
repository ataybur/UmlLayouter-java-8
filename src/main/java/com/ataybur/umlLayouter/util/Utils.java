/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ataybur.umlLayouter.entity.Coordinate;
import com.ataybur.umlLayouter.entity.Edge;
import com.ataybur.umlLayouter.entity.Force;
import com.ataybur.umlLayouter.entity.Graph;
import com.ataybur.umlLayouter.entity.Line;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.service.algorithm.main.AlgorithmMain;

/**
 *
 * @author atay
 */
public class Utils {

    public static Double returnForceForAdjacentNode(Double c1, Double distance, Double c2) {
	if (distance == 0D) {
	    return 0D;
	}
	return c1 * Math.log10(Math.abs(distance) / c2);
    }

    public static Double returnForceForUnadjacentNode(Double c3, Double distance) {
	if (distance == 0D) {
	    return 0D;
	}
	return c3 / (distance * distance);
    }

    public static Force sumTwoForces(Force f1, Force f2) {
	Double xForce = f1.getX() + f2.getX();
	Double yForce = f1.getY() + f2.getY();
	return new Force(xForce, yForce);
    }

    public static Force returnForce(Vertex v1, Vertex v2) {
	Double xDistance = v1.getCoordinate().getX() - v2.getCoordinate().getX();
	Double yDistance = v1.getCoordinate().getY() - v2.getCoordinate().getY();
	return new Force(xDistance, yDistance);
    }

    public static Vertex moveTheVertex(Vertex vertex, Force force) {
	Double xForceDistance = force.getX();
	Double yForceDistance = force.getY();
	Double xCoordinate = vertex.getCoordinate().getX() + xForceDistance;
	Double yCoordinate = vertex.getCoordinate().getY() + yForceDistance;
	vertex.getCoordinate().setX(xCoordinate);
	vertex.getCoordinate().setY(yCoordinate);
	return vertex;
    }

    public static Vertex createVertex(String name) {
	Vertex vertex = new Vertex(name);
	vertex.setCoordinate(createRandomCoordinate(vertex));
	return vertex;
    }

    public static Vertex createVertex1(String name) {
	return new Vertex(name);
    }

    public static Coordinate createRandomCoordinate(Vertex vertex) {
	Coordinate coordinate = new Coordinate();
	Double newDouble = (new Random().nextDouble()) * ProjectConstants.RANDOM_CONSTANT;
	coordinate.setX(newDouble);
	newDouble = (new Random().nextDouble()) * ProjectConstants.RANDOM_CONSTANT;
	coordinate.setY(newDouble);
	return coordinate;
    }

    public static List<Vertex> returnAdjacentList(Graph graph, Vertex vertex) {
	return returnAdjacentList(graph.getEdgeList(), graph.getVertexList(), vertex);
    }

    public static List<Vertex> returnUnadjacentList(Graph graph, Vertex vertex) {
	return returnUnadjacentList(graph.getEdgeList(), graph.getVertexList(), vertex);
    }

    public static List<Vertex> returnAdjacentList(List<Edge> edgeList, List<Vertex> vertexList, Vertex vertex) {
	List<String> adjacentStringList = returnAdjacentStringList(edgeList, vertex);
	return returnVertexListByNameList(adjacentStringList, vertexList);
    }

    public static List<Vertex> returnUnadjacentList(List<Edge> edgeList, List<Vertex> vertexList, Vertex vertex) {
	List<String> adjacentStringList = returnAdjacentStringList(edgeList, vertex);
	List<String> unadjacentStringList = returnUnadjacentStringList(adjacentStringList, vertexList);
	return returnVertexListByNameList(unadjacentStringList, vertexList);
    }

    public static List<String> returnAdjacentStringList(List<Edge> edgeList, Vertex vertex) {
	String adjacentName;
	List<String> adjacentStringList = new ArrayList<String>();
	for (Edge edge : edgeList) {
	    adjacentName = returnAdjacentNameFromEdge(edge, vertex);
	    if (adjacentName != null) {
		adjacentStringList.add(adjacentName);
	    }
	}
	return adjacentStringList;
    }

    public static List<String> returnUnadjacentStringList(List<String> adjacentStringList, List<Vertex> vertexList) {
	List<String> unadjacentStringList = new ArrayList<String>();
	for (Vertex vertex : vertexList) {
	    if (!adjacentStringList.contains(vertex.getName())) {
		unadjacentStringList.add(vertex.getName());
	    }
	}
	return unadjacentStringList;
    }

    public static List<Vertex> returnVertexListByNameList(List<String> nameList, List<Vertex> vertexList) {
	List<Vertex> adjacentVertexList = new ArrayList<Vertex>();
	Vertex adjacentVertex;
	for (String adjacentVertexName : nameList) {
	    adjacentVertex = getVertexByName(adjacentVertexName, vertexList);
	    if (isValidVertex(adjacentVertex)) {
		adjacentVertexList.add(adjacentVertex);
	    }
	}
	return adjacentVertexList;
    }

    public static Vertex getVertexByName(String name, List<Vertex> vertexList) {
	for (Vertex vertex : vertexList) {
	    if (vertex.getName().equalsIgnoreCase(name)) {
		return vertex;
	    }
	}
	return null;
    }

    private static Boolean isValidVertex(Object vertex) {
	if (vertex == null) {
	    return false;
	} else {
	    return true;
	}
    }

    public static Double returnMinOrdinate(List<Vertex> vertexList) {
	Double minOrdinate = Double.MAX_VALUE;
	for (Vertex vertex : vertexList) {
	    if (minOrdinate > vertex.getCoordinate().getY()) {
		minOrdinate = vertex.getCoordinate().getY();
	    }
	}
	return minOrdinate;
    }

    public static Double returnMaxOrdinate(List<Vertex> vertexList) {
	Double maxOrdinate = Double.MIN_VALUE;
	for (Vertex vertex : vertexList) {
	    if (maxOrdinate < vertex.getCoordinate().getY()) {
		maxOrdinate = vertex.getCoordinate().getY();
	    }
	}
	return maxOrdinate;
    }

    public static Double returnMinAxis(List<Vertex> vertexList) {
	Double minAxis = Double.MAX_VALUE;
	for (Vertex vertex : vertexList) {
	    if (minAxis > vertex.getCoordinate().getX()) {
		minAxis = vertex.getCoordinate().getX();
	    }
	}
	return minAxis;
    }

    public static Double returnMaxAxis(List<Vertex> vertexList) {
	Double maxAxis = Double.MIN_VALUE;
	for (Vertex vertex : vertexList) {
	    if (maxAxis < vertex.getCoordinate().getX()) {
		maxAxis = vertex.getCoordinate().getX();
	    }
	}
	return maxAxis;
    }

    private static String returnAdjacentNameFromEdge(Edge edge, Vertex vertex) {
	if (edge.getFirstVertexName().equalsIgnoreCase(vertex.getName())) {
	    return edge.getSecondVertexName();
	} else if (edge.getSecondVertexName().equalsIgnoreCase(vertex.getName())) {
	    return edge.getFirstVertexName();
	} else {
	    return null;
	}
    }

    public static List<Vertex> updateVertex(List<Vertex> vertexList, Vertex vertex) {
	getVertexByName(vertex.getName(), vertexList).setCoordinate(vertex.getCoordinate());
	return vertexList;
    }

    public static Boolean areThereConflictedEdges(Edge firstEdge, Edge secondEdge, Graph graph) {
	System.out.println("areThereConflictedEdges Starts ");
	Line firstLine = returnLine(firstEdge, graph);
	Line secondLine = returnLine(secondEdge, graph);
	String whichLineInvalid = "";
	Boolean result = areThereConflictedLines(firstLine, secondLine, whichLineInvalid);
	System.out.println("firstLine: " + firstEdge.getFirstVertexName() + " - " + firstEdge.getSecondVertexName() + ": " + firstLine.getFirstPoint() + " - " + firstLine.getSecondPoint());
	System.out.println("secondLine: " + secondEdge.getFirstVertexName() + " - " + secondEdge.getSecondVertexName() + ": " + secondLine.getFirstPoint() + " - " + secondLine.getSecondPoint());
	System.out.println("areThereConflictedEdges Ends ");
	return result;
    }

    public static Boolean areThereConflictedLines(Line firstLine, Line secondLine, String whichLineInvalid) {
	System.out.println("areThereConflictedLines Starts ");
	System.out.println("firstLine: " + firstLine);
	System.out.println("secondLine: " + secondLine);
	if (!isLineValid(firstLine)) {
	    /*
	     * TODO
	     * 
	     * Valid olmayan ayrıtlar daha sonra valid hale geldiklerinde
	     * kesişen koordinatlara sahip olabiliyor.
	     * 
	     * Bunu önlemek için ne yapmalı?
	     * 
	     */
	    whichLineInvalid = whichLineInvalid.concat("1");
	}
	if (!isLineValid(secondLine)) {
	    whichLineInvalid = whichLineInvalid.concat("2");
	}
	System.out.println("whichLineInvalid: " + whichLineInvalid);
	if (whichLineInvalid.length() > 0) {
	    System.out.println("areThereConflictedLines Ends false");
	    return false;
	}
	if (areLinesCollide(firstLine, secondLine)) {
	    System.out.println("areThereConflictedLines Ends true");
	    return true;
	} else if (areLinesHasSamePoints(firstLine, secondLine)) {
	    System.out.println("areThereConflictedLines Ends false");
	    return false;
	}

	Double d = calculateD(firstLine, secondLine);

	System.out.println("Is it collided?: " + d);
	if (d == null) {
	    System.out.println("areThereConflictedLines Ends true");
	    return true;
	} else {
	    Double x = calculateIntersectionX(firstLine, secondLine);
	    Double y = calculateIntersectionY(firstLine, x);

	    Boolean result = isPointOnALine(x, y, firstLine);
	    System.out.println("areThereConflictedLines Ends " + result);
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

    private static Boolean isPointOnALine(Double x, Double y, Line line) {
	Boolean forX = isPointBetweenTwoPoint(x, line.getFirstPoint().getX(), line.getSecondPoint().getX());
	Boolean forY = isPointBetweenTwoPoint(y, line.getFirstPoint().getY(), line.getSecondPoint().getY());
	return forX && forY;
    }

    private static Boolean isPointBetweenTwoPoint(Double point, Double coordinatePoint1, Double coordinatePoint2) {
	if (coordinatePoint1 < coordinatePoint2) {
	    return (coordinatePoint1 < point) && (coordinatePoint2 > point);
	} else {
	    return (coordinatePoint2 < point) && (coordinatePoint1 > point);
	}
    }

    private static Double calculateD(Line firstLine, Line secondLine) {
	Double AX = firstLine.getFirstPoint().getX();
	Double AY = firstLine.getFirstPoint().getY();
	Double BX = firstLine.getSecondPoint().getX();
	Double BY = firstLine.getSecondPoint().getY();

	Double CX = secondLine.getFirstPoint().getX();
	Double CY = secondLine.getFirstPoint().getY();
	Double DX = secondLine.getSecondPoint().getX();
	Double DY = secondLine.getSecondPoint().getY();

	Double first = ((DY - CY) * (BX - AX)) - ((BY - AY) * (DX - CX));
	if (first.equals(0D)) {
	    return null;
	}
	Double second = ((DY - CY) * (CX - AX)) - ((CY - AY) * (DX - CX));
	return second / first;
    }

    private static Double calculateIntersectionX(Line firstLine, Line secondLine) {
	// Double AX = firstLine.getFirstPoint().getX();
	// Double AY = firstLine.getFirstPoint().getY();
	// Double BX = firstLine.getSecondPoint().getX();
	// Double BY = firstLine.getSecondPoint().getY();
	//
	// return AX + (BX - AX) * d;
	Double a1 = firstLine.getSlope();
	Double a2 = secondLine.getSlope();
	Double b1 = firstLine.getIntercept();
	Double b2 = secondLine.getIntercept();
	return (b2 - b1) / (a1 - a2);
    }

    private static Double calculateIntersectionY(Line firstLine, Double x) {
	return 1D;
	// return firstLine.getSlope() * x + firstLine.getIntercept();
    }

    private static Boolean areLinesCollide(Line firstLine, Line secondLine) {
	Boolean result = false;
	if (firstLine.getSlope().equals(secondLine.getSlope()) || firstLine.getSlope().equals(-secondLine.getSlope())) {
	    result = areLinesHasSamePoints(firstLine, secondLine);
	}
	return result;
    }

    private static Boolean areLinesHasSamePoints(Line firstLine, Line secondLine) {
	Boolean result = false;
	if (firstLine.getFirstPoint().equals(secondLine.getFirstPoint())) {
	    result = true;
	} else if (firstLine.getSecondPoint().equals(secondLine.getSecondPoint())) {
	    result = true;
	} else if (firstLine.getFirstPoint().equals(secondLine.getSecondPoint())) {
	    result = true;
	} else if (firstLine.getSecondPoint().equals(secondLine.getFirstPoint())) {
	    result = true;
	}
	return result;
    }

    public static Line returnLine(Edge edge, Graph graph) {
	Vertex firstVertex = Utils.getVertexByName(edge.getFirstVertexName(), graph.getVertexList());
	Vertex secondVertex = Utils.getVertexByName(edge.getSecondVertexName(), graph.getVertexList());
	return new Line(firstVertex.getCoordinate(), secondVertex.getCoordinate());
    }

    public static List<Edge> returnEdgeListWithoutThisVertex(List<Edge> assignedEdgeList, Vertex vertex) {
	List<Edge> newEdgeList = new ArrayList<Edge>();
	for (Edge edge : assignedEdgeList) {
	    if (!isEdgeContainVertex(edge, vertex)) {
		newEdgeList.add(edge);
	    }
	}
	return newEdgeList;
    }

    public static List<Edge> returnEdgeListWithinThisVertex(List<Edge> assignedEdgeList, Vertex vertex) {
	List<Edge> newEdgeList = new ArrayList<Edge>();
	for (Edge edge : assignedEdgeList) {
	    if (isEdgeDirectedContainVertex(edge, vertex)) {
		newEdgeList.add(edge);
	    }
	}
	return newEdgeList;
    }

    public static List<Edge> returnEdgeListByParentVertex(List<Edge> assignedEdgeList, Vertex vertex) {
	List<Edge> newEdgeList = new ArrayList<Edge>();
	for (Edge edge : assignedEdgeList) {
	    if (!edge.getIsAssociated() && isEdgeDirectedContainVertex(edge, vertex)) {
		newEdgeList.add(edge);
	    }
	}
	return newEdgeList;
    }

    public static List<Edge> returnGeneralizedEdgeList(List<Edge> edgeList) {
	List<Edge> generalizedEdgeList = new ArrayList<Edge>();
	for (Edge edge : edgeList) {
	    if (!edge.getIsAssociated()) {
		generalizedEdgeList.add(edge);
	    }
	}
	return generalizedEdgeList;
    }

    public static List<Vertex> returnChildVertices(List<Edge> edgeList, List<Vertex> vertexList, String parent) {
	List<String> childVertexName = returnChildVertixNameList(edgeList, parent);
	return Utils.returnVertexListByNameList(childVertexName, vertexList);
    }

    public static List<String> returnChildVertixNameList(List<Edge> edgeList, String parent) {
	List<Edge> generalizedEdgeList = returnGeneralizedEdgeList(edgeList);
	List<String> childVertexName = new ArrayList<String>();
	for (Edge edge : generalizedEdgeList) {
	    if (edge.getSecondVertexName().equalsIgnoreCase(parent)) {
		childVertexName.add(edge.getFirstVertexName());
	    }
	}
	return childVertexName;
    }

    public static List<String> returnParentList(List<Edge> edgeList) {
	List<Edge> generalizedEdgeList = returnGeneralizedEdgeList(edgeList);
	List<String> parentList = new ArrayList<String>();
	for (Edge edge : generalizedEdgeList) {
	    if (!parentList.contains(edge.getSecondVertexName())) {
		parentList.add(edge.getSecondVertexName());
	    }
	}
	return parentList;
    }

    public static List<String> returnParentList(List<Edge> edgeList, String childName) {
	List<Edge> generalizedEdgeList = returnGeneralizedEdgeList(edgeList);
	List<String> parentList = new ArrayList<String>();
	for (Edge edge : generalizedEdgeList) {
	    if (!parentList.contains(edge.getSecondVertexName()) && edge.getFirstVertexName().equalsIgnoreCase(childName)) {
		parentList.add(edge.getSecondVertexName());
	    }
	}
	return parentList;
    }

    public static void setCoordinateToChild(List<Edge> edgeList, List<Vertex> vertexList, String parent) {
	List<Vertex> childVertexList = returnChildVertices(edgeList, vertexList, parent);
	Vertex parentVertex = Utils.getVertexByName(parent, vertexList);
	Graph graph = new Graph();
	List<Vertex> childrenAndParentVertexList = new ArrayList<Vertex>(childVertexList);
	childrenAndParentVertexList.add(parentVertex);
	graph.setVertexList(vertexList);
	graph.setEdgeList(returnEdgeListByParentVertex(edgeList, parentVertex));
	AlgorithmMain algorithmMain = new AlgorithmMain();
	List<Vertex> newVertexList = algorithmMain.doAlgorithm(graph);
	parentVertex = Utils.getVertexByName(parent, vertexList);
	Vertex newParentVertex = Utils.getVertexByName(parent, newVertexList);
	Force force = Utils.returnForce(parentVertex, newParentVertex);
	Double ordinate = newParentVertex.getCoordinate().getY();
	boolean isFirst = true;
	for (Vertex vertex : childrenAndParentVertexList) {
	    vertex.setCoordinate(algorithmMain.moveTheVertexForAlgorithm(vertex, force).getCoordinate());
	    Utils.getVertexByName(vertex.getName(), vertexList).setCoordinate(vertex.getCoordinate());
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

    public static Boolean isEdgeContainVertex(Edge edge, Vertex vertex) {
	return edge.getFirstVertexName().equalsIgnoreCase(vertex.getName()) || edge.getSecondVertexName().equalsIgnoreCase(vertex.getName());
    }

    public static Boolean isEdgeDirectedContainVertex(Edge edge, Vertex vertex) {
	return edge.getSecondVertexName().equalsIgnoreCase(vertex.getName());
    }

    public static Boolean isEdgeValid(Edge edge, Graph graph) {
	return isLineValid(returnLine(edge, graph));
    }

    public static Boolean isLineValid(Line line) {
	if (!isCoordinateValid(line.getFirstPoint())) {
	    return false;
	}
	if (!isCoordinateValid(line.getSecondPoint())) {
	    return false;
	}
	return true;
    }

    public static Boolean isCoordinateValid(Coordinate coordinate) {
	return !coordinate.getX().equals(0D) && !coordinate.getY().equals(0D);
    }

    public static List<Vertex> makeEdgeShort(Edge edge, List<Vertex> vertexList, Double xDistance, Double yDistance) {
	Vertex firstVertex = Utils.getVertexByName(edge.getFirstVertexName(), vertexList);
	Vertex secondVertex = Utils.getVertexByName(edge.getSecondVertexName(), vertexList);
	Line line = new Line(firstVertex, secondVertex);
	Vertex vertexTemp = returnOneDegreeVertex(firstVertex, secondVertex);
	if (vertexTemp == null) {
	    return vertexList;
	}
	vertexTemp = makeVertexShift(line, vertexTemp, xDistance, yDistance);
	return Utils.updateVertex(vertexList, vertexTemp);
    }

    public static Vertex returnOneDegreeVertex(Vertex firstVertex, Vertex secondVertex) {
	if (firstVertex.getDegree() <= secondVertex.getDegree()) {
	    return firstVertex;
	} else {
	    return secondVertex;
	}
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

    public static List<Vertex> fitEdgeIntoScreen(Edge edge, List<Vertex> vertexList, Integer matrixSize) {
	Force distance = returnDistanceForEdge(edge, vertexList);
	Double xDistance = distance.getX();
	Double yDistance = distance.getY();
	Double newXDistance = shiftableDistance(xDistance, matrixSize);
	Double newYDistance = shiftableDistance(yDistance, matrixSize);
	System.out.println(edge + ", oldXDistance: " + xDistance + ", oldYDistance: " + yDistance);
	System.out.println(edge + ", newXDistance: " + newXDistance + ", newYDistance: " + newYDistance);
	return makeEdgeShort(edge, vertexList, newXDistance, newYDistance);
    }

    private static Double shiftableDistance(Double distance, Integer matrixSize) {
	Double doubleMatrixSize = new Double((matrixSize / 8));
	Double absDistance = Math.abs(distance);
	if (absDistance <= doubleMatrixSize) {
	    return 0D;
	} else {
	    return doubleMatrixSize - absDistance;
	}
    }

    private static Force returnDistanceForEdge(Edge edge, List<Vertex> vertexList) {
	Vertex firstVertex = Utils.getVertexByName(edge.getFirstVertexName(), vertexList);
	Vertex secondVertex = Utils.getVertexByName(edge.getSecondVertexName(), vertexList);
	Double xDistance = firstVertex.getCoordinate().getX() - secondVertex.getCoordinate().getX();
	Double yDistance = firstVertex.getCoordinate().getY() - secondVertex.getCoordinate().getY();
	return new Force(xDistance, yDistance);
    }

    public List<Vertex> removeUnadjacent(List<Vertex> vertexList, Vertex vertex) {
	return removeUnadjacent(vertexList, vertex.getName());
    }

    public List<Vertex> removeUnadjacent(List<Vertex> vertexList, String vertexName) {
	int vertexIndex = 0;
	int i = 0;
	for (Vertex vertex : vertexList) {
	    if (vertex.getName().equalsIgnoreCase(vertexName)) {
		vertexIndex = i;
		break;
	    }
	    i++;
	}
	vertexList.remove(vertexIndex);
	return vertexList;
    }

    public static void printGraph(Graph graph) {

	String vertexName;
	System.out.println("List<Vertex> vertexList = new ArrayList<Vertex>();");
	for (Vertex vertex : graph.getVertexList()) {
	    vertexName = vertex.getName();
	    System.out.println("Coordinate " + vertexName + " = new Coordinate(" + vertex.getCoordinate().getX() + "," + vertex.getCoordinate().getY() + ");");
	    System.out.println("Vertex vertex" + vertexName + " = new Vertex(\"" + vertexName + "\");");
	    System.out.println("vertex" + vertexName + ".setCoordinate(" + vertexName + ");");
	    System.out.println("vertexList.add(vertex" + vertexName + ");");
	}
	String first, second;
	System.out.println("List<Edge> edgeList = new ArrayList<Edge>();");
	for (Edge edge : graph.getEdgeList()) {
	    first = edge.getFirstVertexName();
	    second = edge.getSecondVertexName();
	    System.out.println("Edge edge" + second + first + " = new Edge(vertex" + first + ", vertex" + second + ", " + edge.getIsAssociated() + ");");
	    System.out.println("edgeList.add(edge" + second + first + ");");
	}
    }

    public static boolean isListContainEdge(List<Edge> edgeList, Edge edge) {
	for (Edge edgeTemp : edgeList) {
	    if (edgeTemp.equals(edge)) {
		return true;
	    }
	}
	return false;
    }

    public Coordinate getCoordinateViaVertex(Edge edge, Vertex vertex, Graph graph) {
	Line edgeLine = Utils.returnLine(edge, graph);
	if (edge.getFirstVertexName().equalsIgnoreCase(vertex.getName())) {
	    return edgeLine.getFirstPoint();
	} else if (edge.getSecondVertexName().equalsIgnoreCase(vertex.getName())) {
	    return edgeLine.getSecondPoint();
	} else {
	    return null;
	}
    }

    public Boolean isGraphContainVertex(Graph graph, Vertex vertex) {
	return isGraphContainVertexName(graph, vertex.getName());
    }

    public Boolean isVertexListContainVertex(List<Vertex> vertexList, Vertex vertex) {
	return isVertexListContainVertexName(vertexList, vertex.getName());
    }

    public Boolean isGraphContainVertexName(Graph graph, String vertexName) {
	return isVertexListContainVertexName(graph.getVertexList(), vertexName);
    }

    public Boolean isVertexListContainVertexName(List<Vertex> vertexList, String vertexName) {
	for (Vertex vertex : vertexList) {
	    if (vertex.getName().equalsIgnoreCase(vertexName)) {
		return true;
	    }
	}
	return false;
    }
}
