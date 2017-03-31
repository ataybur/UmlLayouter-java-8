/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.termproject;

import javax.swing.JFrame;

import com.ataybur.umlLayouter.service.gui.main.GuiMain;

/**
 *
 * @author atay
 */
public class TermProject {

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
	JFrame world;
	world = new GuiMain().application();
	world.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	world.setVisible(true);
    }
}
