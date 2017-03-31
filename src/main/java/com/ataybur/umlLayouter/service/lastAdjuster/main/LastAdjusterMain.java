/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.lastAdjuster.main;

import com.ataybur.umlLayouter.entity.Coordinate;
import com.ataybur.umlLayouter.entity.Graph;
import com.ataybur.umlLayouter.service.lastAdjuster.service.LastAdjusterService;

/**
 *
 * @author atay
 */
public class LastAdjusterMain extends LastAdjusterService {

    public Graph fitGraphIntoScreen(Graph graph, Integer screenSize) {
	return super.fitGraphIntoScreen(graph, screenSize);
    }

    public Graph adjustGraph(Graph graph) {
	return super.adjustGraph(graph);
    }

    public Coordinate getSumOfWidthAndHeightForBeyondPane(Graph graph) {
	return super.getSumOfWidthAndHeightForBeyondPane(graph);
    }
}
