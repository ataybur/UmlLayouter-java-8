package com.ataybur.umlLayouter.service.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import com.ataybur.umlLayouter.service.gui.main.GuiMain;
import com.ataybur.umlLayouter.service.gui.service.CustomJGraph;
import com.ataybur.umlLayouter.util.ProjectConstants;
import com.ataybur.umlLayouter.util.SimpleCache;

public class OpenButton extends JButton {

    private static final long serialVersionUID = 5895349926909541122L;
    private SimpleCache simpleCache = SimpleCache.INSTANCE;
    private GuiMain guiMain;
    private CustomJGraph jGraph;

    public OpenButton(GuiMain guiMain, CustomJGraph jGraph) {
	super("Aç");
	this.guiMain = guiMain;
	this.jGraph = jGraph;
	addActionListener(new OpenL());
    }

    class OpenL implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
	    JFileChooser c = new JFileChooser();
	    String graphFileName = null;
	    int rVal = c.showOpenDialog(guiMain);
	    if (rVal == JFileChooser.APPROVE_OPTION) {
		graphFileName = c.getSelectedFile().getAbsolutePath();
	    }
	    if (rVal == JFileChooser.CANCEL_OPTION) {
		if (graphFileName != null) {
		    return;
		}
		graphFileName = null;
	    }
	    // g = new ListenableDirectedGraph(DefaultEdge.class);
	    // m_jgAdapter = new JGraphModelAdapter(g);
	    // fillContent();
	    boolean isAppropiedFileName = guiMain.controlGraphFileName(graphFileName);
	    if (isAppropiedFileName) {
		jGraph.fillGraph(graphFileName);
		simpleCache.put(ProjectConstants.GRAPH_FILE_NAME_STRING, graphFileName);
	    }
	}

    }
}
