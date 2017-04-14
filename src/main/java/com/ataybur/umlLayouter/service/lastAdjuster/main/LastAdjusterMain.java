/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.lastAdjuster.main;

import com.ataybur.umlLayouter.entity.Coordinate;
import com.ataybur.umlLayouter.entity.CustomGraph;
import com.ataybur.umlLayouter.service.lastAdjuster.service.LastAdjusterService;

/**
 *
 * @author atay
 */
public class LastAdjusterMain extends LastAdjusterService {

    public CustomGraph fitGraphIntoScreen(CustomGraph graph) {
	return super.fitGraphIntoScreen(graph);
    }

    public CustomGraph adjustGraph(CustomGraph graph) {
	return super.adjustGraph(graph);
    }

    public Coordinate getSumOfWidthAndHeightForBeyondPane(CustomGraph graph) {
	return super.getSumOfWidthAndHeightForBeyondPane(graph);
    }
}
