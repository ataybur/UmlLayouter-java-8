package com.ataybur.umlLayouter.service.gui.service;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.jgraph.JGraph;

import com.ataybur.umlLayouter.entity.CustomGraph;
import com.ataybur.umlLayouter.entity.Vertex;
import com.ataybur.umlLayouter.service.graphDrawer.service.GraphDrawer;

public class CustomJGraph extends JGraph {

    private static final long serialVersionUID = 8459129964149391320L;
    private CustomModelAdapter modelAdapter;

    public CustomJGraph() {
	super(new CustomModelAdapter());
	modelAdapter = (CustomModelAdapter) super.getModel();
    }

    @Override
    public CustomModelAdapter getModel() {
	return modelAdapter;
    }

    public void fillGraph(String graphFileName) {
  	CustomGuiGraph guiGraph = modelAdapter.getGuiGraph();

  	CustomGraph graph = new GraphDrawer(graphFileName).drawGraph();
  	graph.getVertexList() //
  		.stream() //
  		.map(Vertex::getName) //
  		.forEach(guiGraph::addVertex);

  	graph.getEdgeList() //
  		.forEach(guiGraph::addEdge);

  	graph.getVertexList() //
  		.stream() //
  		.map(modelAdapter::getVertexCell) //
  		.map(CustomGraphCell::positionVertexAt) //
  		.forEach(modelAdapter::edit);

  	// for (Vertex vertex : graph.getVertexList()) {
  	// CustomGraphCell cell = customModelAdapter.getVertexCell(vertex);
  	// CellAttributeMap cellAttr = cell.positionVertexAt();
  	// customModelAdapter.edit(cellAttr);
  	// }
      }
    
    public void makePanelImage(String filePath, String extension) {
	Dimension size = this.getSize();
	BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
	Graphics2D g2 = image.createGraphics();
	this.paint(g2);
	if (!filePath.toLowerCase(Locale.ENGLISH).endsWith("." + extension.toLowerCase(Locale.ENGLISH))) {
	    filePath += "." + extension;
	}
	try {
	    ImageIO.write(image, extension.toLowerCase(Locale.ENGLISH), new File(filePath));
	    System.out.println("Panel saved as Image.");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
