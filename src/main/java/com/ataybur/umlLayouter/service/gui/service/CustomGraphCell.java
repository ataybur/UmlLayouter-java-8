package com.ataybur.umlLayouter.service.gui.service;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import com.ataybur.umlLayouter.entity.Coordinate;

public class CustomGraphCell extends DefaultGraphCell {

    private static final long serialVersionUID = -3526629720334069436L;
    
    private DefaultGraphCell instance;
    
    private Coordinate coordinate;
    
    public CustomGraphCell(DefaultGraphCell instance, Coordinate coordinate){
	this.instance = instance;
	this.coordinate = coordinate;
    }
    
    public CellAttributeMap positionVertexAt() {
	CellAttributeMap cellAttr = new CellAttributeMap();
	CustomRectangle rectangle = new CustomRectangle(this.coordinate);
	AttributeMap attr = this.instance.getAttributes();
	GraphConstants.setBounds(attr, rectangle);
	cellAttr.put(this.instance, attr);
	return cellAttr;	
    }
    
}
