package com.ataybur.umlLayouter.service.gui.service;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

public class CustomModelAdapter extends JGraphModelAdapter<String, DefaultEdge>{
    
    private static final long serialVersionUID = -4729160339115319699L;
    
    ListenableGraph<String, DefaultEdge> jGraphT;
    public CustomModelAdapter(){
	this(new ListenableDirectedGraph<String, DefaultEdge>(DefaultEdge.class));
    }
    
    public CustomModelAdapter(ListenableDirectedGraph<String, DefaultEdge> jGraphT) {
	super(jGraphT);
	this.jGraphT = jGraphT;
    }
    
    public ListenableGraph<String, DefaultEdge> getjGraphT() {
	return jGraphT;
    }
    

}
