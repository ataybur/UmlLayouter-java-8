/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.service.gui.service;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JOptionPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;

import com.ataybur.umlLayouter.entity.Edge;
import com.ataybur.umlLayouter.entity.Graph;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.service.graphDrawer.main.GraphDrawerMain;
import com.ataybur.umlLayouter.util.ProjectConstants;

/**
 *
 * @author atay
 */
abstract public class GuiService extends JApplet {

    protected void handleGraphFile(String graphFileName, ListenableGraph g, JGraphModelAdapter m_jgAdapter) {
        if (graphFileName == null) {
            JOptionPane.showMessageDialog(this,
                    "XMI dosyası seçilmedi!",
                    "Uyarı",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!graphFileName.toUpperCase(Locale.ENGLISH).endsWith(".XMI")) {
            JOptionPane.showMessageDialog(this,
                    "Seçilen dosya XMI dosyası değil!",
                    "Uyarı",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        GraphDrawerMain graphDrawerMain = new GraphDrawerMain();
        Graph graph = graphDrawerMain.drawGraph(graphFileName);
        fillGraph(graph, g, m_jgAdapter);
    }

    protected void fillGraph(Graph graph, ListenableGraph g, JGraphModelAdapter m_jgAdapter) {
        System.out.println("fillGraph");
        
        for (Vertex vertex : graph.getVertexList()) {
            g.addVertex(vertex.getName());
            System.out.println(vertex);
        }

        System.out.println("fillGraph");
        for (Edge edge : graph.getEdgeList()) {
            g.addEdge(edge.getFirstVertexName(), edge.getSecondVertexName());
            System.out.println(edge);
        }

        for (Vertex vertex : graph.getVertexList()) {
            positionVertexAt(m_jgAdapter, vertex.getName(), vertex.getCoordinate().getX().intValue(), vertex.getCoordinate().getY().intValue());
            System.out.println(vertex.getName() + ", xInt: " + vertex.getCoordinate().getX().intValue() + ", yInt: " + vertex.getCoordinate().getY().intValue());
        }
    }

    protected void positionVertexAt(JGraphModelAdapter m_jgAdapter, Object vertex, int x, int y) {
        DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
        Map attr = cell.getAttributes();
        GraphConstants.setBounds(attr, new Rectangle(x, y, (ProjectConstants.MATRIX_UNIT_SIZE / 10) * 5, (ProjectConstants.MATRIX_UNIT_SIZE / 10) * 4));
        Map cellAttr = new HashMap();
        cellAttr.put(cell, attr);
        m_jgAdapter.edit(cellAttr, null, null, null);
    }

    protected void makePanelImage(Component panel, String filePath, String extension) {
        Dimension size = panel.getSize();
        BufferedImage image = new BufferedImage(
                size.width, size.height, BufferedImage.TYPE_INT_RGB);
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

    protected void adjustDisplaySettings(JGraph jg) {
        jg.setPreferredSize(ProjectConstants.DEFAULT_SIZE);
        jg.setEnabled(true);
        jg.setEditable(true);
        jg.setMoveable(true);
        jg.setMoveIntoGroups(true);
        Color c = ProjectConstants.DEFAULT_BG_COLOR;
        String colorStr = null;
        try {
            colorStr = getParameter(ProjectConstants.BG_COLOR_PARAMETER);
        } catch (Exception e) {
        }

        if (colorStr != null) {
            c = Color.decode(colorStr);
        }

        jg.setBackground(c);
    }
}
