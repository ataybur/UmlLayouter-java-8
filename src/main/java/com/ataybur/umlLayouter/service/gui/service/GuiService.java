/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.gui.service;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;

import com.ataybur.umlLayouter.entity.Edge;
import com.ataybur.umlLayouter.entity.Graph;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.service.graphDrawer.main.GraphDrawerMain;
import com.ataybur.umlLayouter.util.ProjectConstants;

/**
 *
 * @author atay
 */
public class GuiService  {

    public void fillGraph(String graphFileName, CustomJGraph jgraph) {
	System.out.println("fillGraph");
   	GraphDrawerMain graphDrawerMain = new GraphDrawerMain();
   	Graph graph = graphDrawerMain.drawGraph(graphFileName);
   	CustomModelAdapter customModelAdapter = jgraph.getModel();
   	ListenableGraph<String, DefaultEdge> jGraphT = customModelAdapter.getjGraphT();
	for (Vertex vertex : graph.getVertexList()) {
	    jGraphT.addVertex(vertex.getName());
	    System.out.println(vertex);
	}

	System.out.println("fillGraph");
	for (Edge edge : graph.getEdgeList()) {
	    jGraphT.addEdge(edge.getFirstVertexName(), edge.getSecondVertexName());
	    System.out.println(edge);
	}

	for (Vertex vertex : graph.getVertexList()) {
	    positionVertexAt(customModelAdapter, vertex.getName(), vertex.getCoordinate().getX().intValue(), vertex.getCoordinate().getY().intValue());
	    System.out.println(vertex.getName() + ", xInt: " + vertex.getCoordinate().getX().intValue() + ", yInt: " + vertex.getCoordinate().getY().intValue());
	}
    }

    protected void positionVertexAt(JGraphModelAdapter<String,DefaultEdge> m_jgAdapter, Object vertex, int x, int y) {
	DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
	AttributeMap attr = cell.getAttributes();
	GraphConstants.setBounds(attr, new Rectangle(x, y, (ProjectConstants.MATRIX_UNIT_SIZE / 10) * 5, (ProjectConstants.MATRIX_UNIT_SIZE / 10) * 4));
	Map<DefaultGraphCell,AttributeMap> cellAttr = new HashMap<DefaultGraphCell,AttributeMap>();
	cellAttr.put(cell, attr);
	m_jgAdapter.edit(cellAttr, null, null, null);
    }

    public void makePanelImage(Component panel, String filePath, String extension) {
	Dimension size = panel.getSize();
	BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
	Graphics2D g2 = image.createGraphics();
	panel.paint(g2);
	if (!filePath.toLowerCase().endsWith("." + extension.toLowerCase())) {
	    filePath += "." + extension;
	}
	try {
	    ImageIO.write(image, extension.toLowerCase(), new File(filePath));
	    System.out.println("Panel saved as Image.");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }


}
