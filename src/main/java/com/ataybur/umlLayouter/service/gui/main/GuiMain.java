package com.ataybur.umlLayouter.service.gui.main;

/**
 * @author atay
 */
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jgraph.JGraph;

import com.ataybur.umlLayouter.service.gui.service.CustomJGraph;
import com.ataybur.umlLayouter.service.gui.service.GuiService;
import com.ataybur.umlLayouter.util.ProjectConstants;

public class GuiMain extends JApplet {

    private static final long serialVersionUID = 8026973495523095685L;
    
    private CustomJGraph jgraph;
    private JButton open = new JButton("Aç");
    private JButton retry = new JButton("Tekrar");
    private JButton screenShot = new JButton("Ekran Görüntüsü Al");
    private JButton openDialog = new JButton("Ayarlar");
    private JTextField repeatNumberForAlgorithm = new JTextField(3);
    private JLabel repeatNumberForAlgorithmLabel = new JLabel("Algoritma İçin Tekrar Sayısı");
    private JTextField repeatNumberForGraphInitiation = new JTextField(3);
    private JLabel repeatNumberForGraphInitiationLabel = new JLabel("İlk Çizge İçin Tekrar Sayısı");
    private JTextField matrixUnitSizeButton = new JTextField(3);
    private JLabel matrixUnitSizeButtonLabel = new JLabel("Grid Kenar Boyu");
    private JTextField repeatNumberForPaneButton = new JTextField(3);
    private JLabel repeatNumberForPaneButtonLabel = new JLabel("Çizge Paneli İçin Tekrar Sayısı");
    private String graphFileName;
    
    private GuiService guiService = new GuiService();

    /**
     * @see java.applet.Applet#init().
     */
    public void init() {
	// create a JGraphT graph
	graphFileName = null;
	JPanel p = new JPanel();
	openDialog.addActionListener(new GuiMain.OpenDialogL());
	p.add(openDialog);
	open.addActionListener(new GuiMain.OpenL());
	p.add(open);
	retry.addActionListener(new GuiMain.RetryL());
	p.add(retry);
	screenShot.addActionListener(new GuiMain.ScreenShotL());
	p.add(screenShot);
	getContentPane().add(p, BorderLayout.NORTH);
	fillContent();
    }

    private void fillContent() {
	adjustDisplaySettings(jgraph);
	getContentPane().add(jgraph);
	resize(ProjectConstants.DEFAULT_SIZE);
    }

    public JFrame application() {
	return new GuiMain.AppletFrame(this);
    }

    private class AppletFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public AppletFrame(Applet p) {
	    setTitle("Paint Application");
	    setSize(ProjectConstants.DEFAULT_SIZE.width, ProjectConstants.DEFAULT_SIZE.height);
	    p.init();
	    p.start();
	    add("Center", p);
	}
    }

    class OpenL implements ActionListener {

	public void actionPerformed(ActionEvent e) {
	    JFileChooser c = new JFileChooser();
	    int rVal = c.showOpenDialog(GuiMain.this);
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
	    handleGraphFile(graphFileName, jgraph);
	}
    }

    class RetryL implements ActionListener {

	public void actionPerformed(ActionEvent e) {
	    handleGraphFile(graphFileName, jgraph);
	}
    }

    class OpenDialogL implements ActionListener {

	public void actionPerformed(ActionEvent evt) {
	    JPanel panelForDialog = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JPanel panelForRepeatNumberForAlgorithm = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JPanel panelForRepeatNumberForGraphInitiation = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JPanel panelForMatrixUnitSizeButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JPanel panelForRepeatNumberForPaneButton = new JPanel(new FlowLayout(FlowLayout.LEFT));

	    repeatNumberForAlgorithm.setText(ProjectConstants.M.toString());
	    repeatNumberForGraphInitiation.setText(ProjectConstants.REPEAT_NUMBER.toString());
	    matrixUnitSizeButton.setText(ProjectConstants.MATRIX_UNIT_SIZE.toString());
	    repeatNumberForPaneButton.setText(ProjectConstants.REPEAT_NUMBER_FOR_PANE.toString());

	    repeatNumberForAlgorithmLabel.setLabelFor(repeatNumberForAlgorithm);
	    panelForRepeatNumberForAlgorithm.add(repeatNumberForAlgorithmLabel);
	    panelForRepeatNumberForAlgorithm.add(repeatNumberForAlgorithm);

	    repeatNumberForGraphInitiationLabel.setLabelFor(repeatNumberForGraphInitiation);
	    panelForRepeatNumberForGraphInitiation.add(repeatNumberForGraphInitiationLabel);
	    panelForRepeatNumberForGraphInitiation.add(repeatNumberForGraphInitiation);

	    matrixUnitSizeButtonLabel.setLabelFor(matrixUnitSizeButton);
	    panelForMatrixUnitSizeButton.add(matrixUnitSizeButtonLabel);
	    panelForMatrixUnitSizeButton.add(matrixUnitSizeButton);

	    repeatNumberForPaneButtonLabel.setLabelFor(repeatNumberForPaneButton);
	    panelForRepeatNumberForPaneButton.add(repeatNumberForPaneButtonLabel);
	    panelForRepeatNumberForPaneButton.add(repeatNumberForPaneButton);

	    repeatNumberForAlgorithm.setHorizontalAlignment(JTextField.RIGHT);
	    repeatNumberForGraphInitiation.setHorizontalAlignment(JTextField.RIGHT);
	    matrixUnitSizeButton.setHorizontalAlignment(JTextField.RIGHT);
	    repeatNumberForPaneButton.setHorizontalAlignment(JTextField.RIGHT);

	    panelForDialog.setLayout(new BoxLayout(panelForDialog, BoxLayout.Y_AXIS));

	    panelForDialog.add(panelForRepeatNumberForAlgorithm);
	    panelForDialog.add(panelForRepeatNumberForGraphInitiation);
	    panelForDialog.add(panelForMatrixUnitSizeButton);
	    panelForDialog.add(panelForRepeatNumberForPaneButton);

	    int result = JOptionPane.showConfirmDialog(null, panelForDialog, "Ayarlar", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
		updateMatrixUnitSize();
		updateRepeatNumberForAlgorithm();
		updateRepeatNumberForGraphInitiation();
		updateRepeatNumberForPane();
	    }
	}
    }

    class ScreenShotL implements ActionListener {

	public void actionPerformed(ActionEvent e) {
	    String targetFile = ".";
	    String extension = "png";
	    JFileChooser c = new JFileChooser();
	    // Demonstrate "Save" dialog:
	    int rVal = c.showSaveDialog(GuiMain.this);
	    if (rVal == JFileChooser.APPROVE_OPTION) {
		targetFile = c.getSelectedFile().getAbsolutePath();
	    }
	    if (rVal == JFileChooser.CANCEL_OPTION) {
		return;
	    }
	    if (targetFile.contains(".")) {
		extension = targetFile.substring(targetFile.lastIndexOf(".") + 1);
	    }
	    guiService.makePanelImage(jgraph, targetFile, extension);
	}
    }

    private void adjustDisplaySettings(JGraph jg) {
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
    
    private void handleGraphFile(String graphFileName, CustomJGraph jgraph) {
   	if (graphFileName == null) {
   	    JOptionPane.showMessageDialog(this, "XMI dosyası seçilmedi!", "Uyarı", JOptionPane.WARNING_MESSAGE);
   	    return;
   	}
   	if (!graphFileName.toUpperCase(Locale.ENGLISH).endsWith(".XMI")) {
   	    JOptionPane.showMessageDialog(this, "Seçilen dosya XMI dosyası değil!", "Uyarı", JOptionPane.WARNING_MESSAGE);
   	    return;
   	}
   	guiService.fillGraph(graphFileName, jgraph);
       }
    
    private void updateMatrixUnitSize() {
	ProjectConstants.MATRIX_UNIT_SIZE = new Integer(matrixUnitSizeButton.getText());
    }

    private void updateRepeatNumberForAlgorithm() {
	ProjectConstants.M = new Integer(repeatNumberForAlgorithm.getText());
    }

    private void updateRepeatNumberForGraphInitiation() {
	ProjectConstants.REPEAT_NUMBER = new Integer(repeatNumberForGraphInitiation.getText());
    }

    private void updateRepeatNumberForPane() {
	ProjectConstants.REPEAT_NUMBER_FOR_PANE = new Integer(repeatNumberForPaneButton.getText());
    }
    
    
}
