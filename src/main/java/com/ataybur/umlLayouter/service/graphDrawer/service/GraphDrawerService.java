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
import com.ataybur.umlLayouter.entity.Graph;
import com.ataybur.umlLayouter.service.algorithm.main.AlgorithmMain;
import com.ataybur.umlLayouter.service.generalizationAdjuster.main.GeneralizationAdjusterMain;
import com.ataybur.umlLayouter.service.graphMLReader.main.GraphMLReaderMain;
import com.ataybur.umlLayouter.service.lastAdjuster.main.LastAdjusterMain;
import com.ataybur.umlLayouter.service.xmiTransformer.main.XmiTransformerMain;
import com.ataybur.umlLayouter.util.ProjectConstants;
import com.ataybur.umlLayouter.util.Utils;

/**
 *
 * @author atay
 */
abstract public class GraphDrawerService {

    protected Graph drawGraph(String graphFileName) {
        Graph newGraph = new Graph();
        SortedMap<Coordinate, Graph> graphPaneSizeMap = new TreeMap<Coordinate, Graph>(coordinateComparator);
        LastAdjusterMain lastAdjusterMain = new LastAdjusterMain();
        for (int i = 0; i < ProjectConstants.REPEAT_NUMBER_FOR_PANE; i++) {
            Graph graph = initiateGraph(graphFileName);
            newGraph = handleGraph(graph);
            Coordinate graphPaneSize = lastAdjusterMain.getSumOfWidthAndHeightForBeyondPane(newGraph);
            graphPaneSizeMap.put(graphPaneSize, newGraph);
        }
        List<Graph> graphList = new ArrayList<Graph>(graphPaneSizeMap.values());
        return graphList.get(0);
    }
    Comparator<Coordinate> coordinateComparator = new Comparator<Coordinate>() {
        public int compare(Coordinate coordinate1, Coordinate coordinate2) {
            return coordinate1.getX().compareTo(ProjectConstants.DEFAULT_SIZE.getWidth()) & coordinate1.getY().compareTo(ProjectConstants.DEFAULT_SIZE.getHeight());
        }
    };

    private Graph initiateGraph(String graphFileName) {
        XmiTransformerMain xmiTransformerMain = new XmiTransformerMain();
        Graph graph = xmiTransformerMain.createGraph(graphFileName);
        GraphMLReaderMain graphMLReaderMain = new GraphMLReaderMain();
        graph = graphMLReaderMain.initiateGraph(graph, ProjectConstants.MATRIX_UNIT_SIZE, ProjectConstants.REPEAT_NUMBER);
        System.out.println("--------------printGraph");
        Utils.printGraph(graph);
        return graph;
    }

    private Graph handleGraph(Graph graph) {
        return doAlgorithm(new Graph(graph));
    }

    private Graph doAlgorithm(Graph graph) {
        LastAdjusterMain lastAdjusterMain = new LastAdjusterMain();
        GeneralizationAdjusterMain generalizationAdjusterMain = new GeneralizationAdjusterMain();
        AlgorithmMain algorithmMain = new AlgorithmMain();
        graph.setVertexList(algorithmMain.doAlgorithm(graph));
        System.out.println("Algoritmadan sonra");
        graph = lastAdjusterMain.fitGraphIntoScreen(graph, ProjectConstants.DEFAULT_SIZE.height);
        generalizationAdjusterMain.doLayoutForGeneralizedEdges(graph);
        return lastAdjusterMain.adjustGraph(graph);
    }
}
