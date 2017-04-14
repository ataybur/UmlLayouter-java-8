/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.graphDrawer.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.ataybur.umlLayouter.entity.Coordinate;
import com.ataybur.umlLayouter.entity.CustomGraph;
import com.ataybur.umlLayouter.service.generalizationAdjuster.main.GeneralizationAdjusterMain;
import com.ataybur.umlLayouter.service.graphMLReader.main.GraphMLReaderMain;
import com.ataybur.umlLayouter.service.gui.service.VertexList;
import com.ataybur.umlLayouter.service.lastAdjuster.main.LastAdjusterMain;
import com.ataybur.umlLayouter.service.xmiTransformer.main.XmiTransformerMain;
import com.ataybur.umlLayouter.util.ProjectConstants;

/**
 * @author atay
 */
public class GraphDrawer {
    private String graphFileName;
    
    public GraphDrawer(String graphFileName){
	super();
	this.graphFileName = graphFileName;
    }
    
    public CustomGraph drawGraph() {
	CustomGraph newGraph = new CustomGraph();
	SortedMap<Coordinate, CustomGraph> graphPaneSizeMap = new TreeMap<Coordinate, CustomGraph>(coordinateComparator);
	LastAdjusterMain lastAdjusterMain = new LastAdjusterMain();
	for (int i = 0; i < ProjectConstants.REPEAT_NUMBER_FOR_PANE; i++) {
	    CustomGraph graph = initiateGraph(graphFileName);
	    newGraph = handleGraph(graph);
	    Coordinate graphPaneSize = lastAdjusterMain.getSumOfWidthAndHeightForBeyondPane(newGraph);
	    graphPaneSizeMap.put(graphPaneSize, newGraph);
	}
	List<CustomGraph> graphList = new ArrayList<CustomGraph>(graphPaneSizeMap.values());
	return graphList.get(0);
    }

    Comparator<Coordinate> coordinateComparator = new Comparator<Coordinate>() {
	public int compare(Coordinate coordinate1, Coordinate coordinate2) {
	    return coordinate1.getX().compareTo(ProjectConstants.DEFAULT_SIZE.getWidth()) & coordinate1.getY().compareTo(ProjectConstants.DEFAULT_SIZE.getHeight());
	}
    };

    private CustomGraph initiateGraph(String graphFileName) {
	XmiTransformerMain xmiTransformerMain = new XmiTransformerMain();
	CustomGraph graph = xmiTransformerMain.createGraph(graphFileName);
	GraphMLReaderMain graphMLReaderMain = new GraphMLReaderMain();
	graph = graphMLReaderMain.initiateGraph(graph, ProjectConstants.MATRIX_UNIT_SIZE, ProjectConstants.REPEAT_NUMBER);
	System.out.println("--------------printGraph");
	graph.printGraph();
	return graph;
    }

    private CustomGraph handleGraph(CustomGraph graph) {
	return doAlgorithm(new CustomGraph(graph));
    }

    private CustomGraph doAlgorithm(CustomGraph graph) {
	LastAdjusterMain lastAdjusterMain = new LastAdjusterMain();
	GeneralizationAdjusterMain generalizationAdjusterMain = new GeneralizationAdjusterMain();
	VertexList newVertexList = graph.doAlgorithm();
	graph.setVertexList(newVertexList);
	System.out.println("Algoritmadan sonra");
	graph = lastAdjusterMain.fitGraphIntoScreen(graph);
	generalizationAdjusterMain.doLayoutForGeneralizedEdges(graph);
	return lastAdjusterMain.adjustGraph(graph);
    }
}
