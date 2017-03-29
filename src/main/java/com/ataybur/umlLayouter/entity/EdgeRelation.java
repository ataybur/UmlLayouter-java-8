/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.entity;

/**
 *
 * @author atay
 */

public enum EdgeRelation {
    HIERARCHICAL("gener.xsl"),ASSOCIATION("assos.xsl");
    private final String xsltFileName;
    private EdgeRelation(String xsltFileName) {
        this.xsltFileName = xsltFileName;
    }

    public String getXsltFileName() {
        return xsltFileName;
    }
}
