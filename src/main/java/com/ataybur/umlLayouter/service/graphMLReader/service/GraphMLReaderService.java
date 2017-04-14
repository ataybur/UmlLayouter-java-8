/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.graphMLReader.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.ataybur.umlLayouter.entity.Coordinate;
import com.ataybur.umlLayouter.entity.CustomGraph;
import com.ataybur.umlLayouter.entity.Edge;
import com.ataybur.umlLayouter.entity.EdgeRelation;
import com.ataybur.umlLayouter.entity.EdgeValidator;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.entity.VertexUtilizer;
import com.ataybur.umlLayouter.service.gui.service.EdgeList;
import com.ataybur.umlLayouter.service.gui.service.VertexList;
import com.ataybur.umlLayouter.util.ProjectConstants;
import com.ataybur.umlLayouter.util.Utils;
import com.google.common.base.Supplier;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.io.GraphMLReader;

/**
 * @author atay
 */
abstract public class GraphMLReaderService {

    protected CustomGraph readGraphml(CustomGraph newGraph, String filename, EdgeRelation edgeRelation) {
	try {
	    Supplier<Number> vertexFactory = new Supplier<Number>() {
		int n = 0;

		public Number get() {
		    return n++;
		}
	    };
	    Supplier<Number> edgeFactory = new Supplier<Number>() {
		int n = 0;

		public Number get() {
		    return n++;
		}
	    };

	    GraphMLReader<DirectedGraph<Number, Number>, Number, Number> gmlr = new GraphMLReader<DirectedGraph<Number, Number>, Number, Number>(vertexFactory, edgeFactory);
	    DirectedGraph<Number, Number> graph = new DirectedSparseMultigraph<Number, Number>();
	    gmlr.load(filename, graph);
	    return convertGraphToMyGraph(graph, newGraph, edgeRelation);
	} catch (IOException ex) {
	    Logger.getLogger(GraphMLReaderService.class.getName()).log(Level.SEVERE, null, ex);
	} catch (ParserConfigurationException ex) {
	    Logger.getLogger(GraphMLReaderService.class.getName()).log(Level.SEVERE, null, ex);
	} catch (SAXException ex) {
	    Logger.getLogger(GraphMLReaderService.class.getName()).log(Level.SEVERE, null, ex);
	}
	return null;
    }

    private CustomGraph convertGraphToMyGraph(DirectedGraph<Number, Number> graph, CustomGraph newGraph, EdgeRelation edgeRelation) {
	return createGraph(newGraph, graph, edgeRelation);
    }

    protected String transform(String fileName, String xslFileName) throws TransformerConfigurationException, TransformerException, ParserConfigurationException, SAXException, IOException {
	TransformerFactory factory = TransformerFactory.newInstance();
	Source xslFile = new StreamSource(new File(xslFileName));

	Transformer transformerAssos = factory.newTransformer(xslFile);
	changeUML(fileName);
	Source text = new StreamSource(new File(fileName));

	fileName = fileName.replaceAll(".xmi", ".graphml");
	transformerAssos.transform(text, new StreamResult(new File(fileName)));
	return fileName;
    }

    public CustomGraph initiateLayoutForGraph(CustomGraph graph, Integer matrixUnitSize) {
	System.out.println("----------createInitiatedGraph ");
	List<Coordinate> matrix = createGridPlane(graph, matrixUnitSize);
	return spreadIntoMatrix(graph, matrix, matrixUnitSize);
    }

    public List<Coordinate> createGridPlane(CustomGraph graph, Integer matrixUnitSize) {
	Integer matrixSize;
	Double matrixSizeDouble;
	List<Coordinate> matrix = new ArrayList<Coordinate>();
	matrixSizeDouble = ProjectConstants.GRID_PLANE_CONSTANT * Math.sqrt(graph.getVertexList().size());
	matrixSize = new Double(Math.ceil(matrixSizeDouble)).intValue();
	for (int i = 1; i <= matrixSize; i++) {
	    for (int j = 1; j <= matrixSize; j++) {
		matrix.add(new Coordinate(matrixUnitSize * i, matrixUnitSize * j));
	    }
	}
	return matrix;
    }

    public CustomGraph spreadIntoMatrix(CustomGraph graph, List<Coordinate> matrix, Integer matrixUnitSize) {
	List<Coordinate> matrixTemp = new ArrayList<Coordinate>(matrix);
	EdgeList invalidEdgeList = new EdgeList();
	EdgeList assignedEdgeList = new EdgeList();
	for (Vertex vertex : graph.getVertexList()) {
	    matrixTemp = generateRandomCoordinate(vertex, matrixTemp, assignedEdgeList, graph, invalidEdgeList);
	    if (matrixTemp == null) {
		graph = initiateLayoutForGraph(graph, matrixUnitSize);
	    }
	}
	for (Vertex vertex : graph.getVertexList()) {
	    if (isConflictedWithGraph(vertex, assignedEdgeList, graph)) {
		graph = initiateLayoutForGraph(graph, matrixUnitSize);
	    }
	}

	return graph;
    }

    private List<Coordinate> generateRandomCoordinate(Vertex vertex, List<Coordinate> matrixTemp, EdgeList assignedEdgeList, CustomGraph graph, EdgeList invalidEdgeList) {
	List<Coordinate> tempCoordinateList = new ArrayList<Coordinate>();
	if (matrixTemp == null || (matrixTemp != null && matrixTemp.size() == 0)) {
	    return null;
	}
	try {
	    Integer randomCoordinateIndex = (new Double((new Random().nextDouble()) * matrixTemp.size())).intValue();
	    Coordinate result = matrixTemp.get(randomCoordinateIndex);

	    for (Coordinate coordinate : matrixTemp) {
		if (!result.equals(coordinate)) {
		    tempCoordinateList.add(coordinate);
		}
	    }
	    vertex.setCoordinate(result);

	    System.out.println("assignedEdgeList.size() >= matrixTemp.size(): " + (assignedEdgeList.size() >= matrixTemp.size()));
	    if (isConflictedWithGraph(vertex, assignedEdgeList, graph)) {
		if (assignedEdgeList.size() >= matrixTemp.size()) {

		    tempCoordinateList = null;
		} else {
		    if (tempCoordinateList == null || (tempCoordinateList != null && tempCoordinateList.isEmpty())) {
			System.out.println();

		    }
		    tempCoordinateList = generateRandomCoordinate(vertex, tempCoordinateList, assignedEdgeList, graph, invalidEdgeList);
		}
	    }
	    assignedEdgeList.clear();
	    assignedEdgeList.addAll(addEdge(assignedEdgeList, vertex, graph, invalidEdgeList));
	    return tempCoordinateList;
	} catch (Exception e) {
	    System.out.println(e);
	    return null;
	}

    }

    private EdgeList addEdge(EdgeList assignedEdgeList, Vertex vertex, CustomGraph graph, EdgeList invalidEdgeList) {
	VertexList adjacentVertexList = graph.returnAdjacentList(vertex);
	Edge newEdge;
	for (Vertex adjacentVertex : adjacentVertexList) {
	    // if (adjacentVertex.getCoordinate()) {
	    boolean defaultEdgeType = false;
	    newEdge = new Edge(vertex, adjacentVertex, defaultEdgeType);
	    if (!assignedEdgeList.isListContainEdge(newEdge)) {
		assignedEdgeList.add(newEdge);
		if (new EdgeValidator(newEdge, graph.getVertexList()).isNotValid()) {
		    invalidEdgeList.add(newEdge);
		    System.out.println("Invalid edge added!");
		}
		System.out.println("New edge added!");
		System.out.println("vertex: " + vertex.getName() + ", adjacentVertex: " + adjacentVertex);
	    }
	    // }
	}
	return assignedEdgeList;
    }

    public Integer howManyIntersection(CustomGraph graph) {
	Integer counter = 0;
	for (Vertex vertex : graph.getVertexList()) {
	    if (isConflictedWithGraph(vertex, graph.getEdgeList(), graph)) {
		counter++;
	    }
	}
	return counter;
    }

    private Boolean isConflictedWithGraph(Vertex vertex, EdgeList assignedEdgeList, CustomGraph graph) {
	System.out.println("isConflictedWithGraph Starts ");
	EdgeList edgeListWithoutThisVertex = graph.getEdgeList().returnEdgeListWithoutThisVertex(vertex);
	VertexList adjacentVertexList = graph.returnAdjacentList(vertex);
	// edgeListWithoutThisVertex.addAll(invalidEdgeList);
	System.out.println("invalidEdgeList Starts ");
	// for (Edge edge : invalidEdgeList) {
	// System.out.println(edge);
	// }
	System.out.println("invalidEdgeList Ends ");
	System.out.println("isConflictedWithGraph: edgeListWithoutThisVertex.size(): " + edgeListWithoutThisVertex.size() + ", VertexName: " + vertex.getName());
	for (Vertex adjacentVertex : adjacentVertexList) {
	    for (Edge edge : edgeListWithoutThisVertex) {
		boolean defaultEdgeType = false;
		if (Utils.areThereConflictedEdges(new Edge(vertex, adjacentVertex, defaultEdgeType), edge, graph)) {
		    // if (isListContainEdge(invalidEdgeList, edge)) {
		    // invalidEdgeList.remove(edge);
		    // edgeListWithoutThisVertex.remove(edge);
		    // }
		    System.out.println("isConflictedWithGraph Ends true");
		    return true;
		}
	    }
	}
	System.out.println("isConflictedWithGraph Ends false");
	return false;
    }

    protected CustomGraph initiateGraph(CustomGraph graph, Integer matrixUnitSize, Integer loopNumber) {
	SortedMap<Integer, CustomGraph> intersectionNumberMap = new TreeMap<Integer, CustomGraph>();
	CustomGraph newGraph = null;
	Integer howManyIntersection = 0;
	for (int i = 0; i < loopNumber; i++) {
	    newGraph = initiateLayoutForGraph(graph, matrixUnitSize);
	    howManyIntersection = howManyIntersection(graph);
	    intersectionNumberMap.put(howManyIntersection, newGraph);
	}
	return intersectionNumberMap.get(intersectionNumberMap.firstKey());
    }

    private File changeUML(String filePath) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {

	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	System.out.println("filePath: " + filePath);
	Document doc = docBuilder.parse(filePath);
	Node xmi = doc.getElementsByTagName("XMI").item(0);
	NamedNodeMap attr = xmi.getAttributes();
	Node nodeAttr = attr.getNamedItem("xmlns:UML");
	nodeAttr.setTextContent("org.omg/UML/1.4");

	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = transformerFactory.newTransformer();
	DOMSource source = new DOMSource(doc);
	StreamResult result = new StreamResult(new File(filePath));
	transformer.transform(source, result);
	return new File(filePath);
    }

    private CustomGraph createGraph(CustomGraph graph, DirectedGraph<Number, Number> graphOrgin, EdgeRelation edgeRelation) {
	graph.setVertexList(new VertexList());
	for (Object vertice : graphOrgin.getVertices()) {
	    String verticeName = String.valueOf(vertice);
	    Vertex randomVertex = new VertexUtilizer(new Vertex(verticeName)).create();
	    graph.getVertexList().add(randomVertex);
	}

	String first;
	String second;
	for (Number edge : graphOrgin.getEdges()) {
	    Object first2 = graphOrgin.getEndpoints(edge).getFirst();
	    Object second2 = graphOrgin.getEndpoints(edge).getSecond();
	    first = first2.toString();
	    second = second2.toString();
	    makeAdjacent(first, second, graph, edgeRelation);
	}
	return graph;
    }

    public void makeAdjacent(Vertex mainVertex, Vertex vertex, CustomGraph graph, EdgeRelation edgeRelation) {
	// mainVertex.getAdjacentVertexList().add(vertex);
	boolean isAssociated = edgeRelation.equals(EdgeRelation.ASSOCIATION);
	graph.getEdgeList().add(new Edge(mainVertex, vertex, isAssociated));
	// removeUnadjacent(mainVertex.getUnadjacentVertexList(), vertex);
    }

    public void makeAdjacent(Vertex mainVertex, String vertexName, CustomGraph graph, EdgeRelation edgeRelation) {
	makeAdjacent(mainVertex, graph.getVertexList().getVertexByName(vertexName), graph, edgeRelation);
    }

    public void makeAdjacent(String mainVertexName, String vertexName, CustomGraph graph, EdgeRelation edgeRelation) {
	makeAdjacent(graph.getVertexList().getVertexByName(mainVertexName), graph.getVertexList().getVertexByName(vertexName), graph, edgeRelation);
    }

    public void makeAdjacent(String mainVertexName, Vertex vertex, CustomGraph graph, EdgeRelation edgeRelation) {
	makeAdjacent(graph.getVertexList().getVertexByName(mainVertexName), vertex, graph, edgeRelation);
    }
}
