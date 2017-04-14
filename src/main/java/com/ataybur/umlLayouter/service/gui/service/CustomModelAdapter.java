package com.ataybur.umlLayouter.service.gui.service;

import org.jgraph.graph.DefaultGraphCell;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;

import com.ataybur.umlLayouter.entity.Vertex;

public class CustomModelAdapter extends JGraphModelAdapter<String, DefaultEdge>{
    
    private static final long serialVersionUID = -4729160339115319699L;
    
    CustomGuiGraph guiGraph;
    public CustomModelAdapter(){
	this(new CustomGuiGraph());
    }
    
    public CustomModelAdapter(CustomGuiGraph guiGraph) {
	super(guiGraph);
	this.guiGraph = guiGraph;
    }
    
    public CustomGuiGraph getGuiGraph() {
	return guiGraph;
    }
    
    public void edit(CellAttributeMap attributes) {
        super.edit(attributes, null, null, null);
    }
    
    public CustomGraphCell getVertexCell(Vertex vertex) {
	DefaultGraphCell defaultGraphCell = super.getVertexCell(vertex.getName());
	CustomGraphCell customGraphCell = new CustomGraphCell(defaultGraphCell, vertex.getCoordinate());
        return customGraphCell;
    }

}
