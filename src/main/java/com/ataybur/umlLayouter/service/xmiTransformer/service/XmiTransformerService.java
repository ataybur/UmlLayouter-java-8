/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.xmiTransformer.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.ataybur.umlLayouter.entity.EdgeRelation;
import com.ataybur.umlLayouter.entity.Graph;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.service.graphMLReader.main.GraphMLReaderMain;
import com.ataybur.umlLayouter.util.ProjectConstants;
import com.ataybur.umlLayouter.util.Utils;

/**
 *
 * @author atay
 */
abstract public class XmiTransformerService {

    protected Graph createGraph(String graphFileName) {
        try {
            Graph newGraph = new Graph();
            newGraph = fillGraphByXSLT(newGraph, graphFileName, EdgeRelation.HIERARCHICAL);
            newGraph = fillGraphByXSLT(newGraph, graphFileName, EdgeRelation.ASSOCIATION);
            newGraph = adjustVertexDegree(newGraph);
            return newGraph;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XmiTransformerService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XmiTransformerService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmiTransformerService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XmiTransformerService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XmiTransformerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Graph fillGraphByXSLT(Graph newGraph, String graphFileName, EdgeRelation edgeRelation) throws TransformerConfigurationException, TransformerException, ParserConfigurationException, SAXException, IOException {
        String filename = ProjectConstants.XSLT_PATH+edgeRelation.getXsltFileName();
        GraphMLReaderMain graphMLReaderMain = new GraphMLReaderMain();
        filename = graphMLReaderMain.transform(graphFileName, filename);
        newGraph = graphMLReaderMain.readGraphml(newGraph, filename, edgeRelation);
        return newGraph;
    }

    private Graph adjustVertexDegree(Graph graph) {
        for (Vertex vertex : graph.getVertexList()) {
            vertex.setDegree(Utils.returnAdjacentStringList(graph.getEdgeList(), vertex).size());
        }
        return graph;
    }
}
