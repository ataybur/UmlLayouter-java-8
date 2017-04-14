/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.graphMLReader.main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.ataybur.umlLayouter.entity.EdgeRelation;
import com.ataybur.umlLayouter.entity.CustomGraph;
import com.ataybur.umlLayouter.service.graphMLReader.service.GraphMLReaderService;

/**
 *
 * @author atay
 */
public class GraphMLReaderMain extends GraphMLReaderService {

    public CustomGraph readGraphml(CustomGraph newGraph, String filename, EdgeRelation edgeRelation) {
	return super.readGraphml(newGraph, filename, edgeRelation);
    }

    public String transform(String fileName, String xslFileName) throws TransformerConfigurationException, TransformerException, ParserConfigurationException, SAXException, IOException {
	return super.transform(fileName, xslFileName);
    }

    public CustomGraph initiateGraph(CustomGraph graph, Integer matrixUnitSize, Integer loopNumber) {
	return super.initiateGraph(graph, matrixUnitSize, loopNumber);
    }
}
