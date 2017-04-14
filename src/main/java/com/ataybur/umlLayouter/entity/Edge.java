/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.entity;

/**
 *
 * @author atay
 */
public class Edge {

    private String firstVertexName;
    private String secondVertexName;
    private Boolean isAssociated;

    public Edge() {
    }

    public Edge(Vertex firstPoint, Vertex secondPoint, Boolean isAssociated) {
	this.firstVertexName = firstPoint.getName();
	this.secondVertexName = secondPoint.getName();
	this.isAssociated = isAssociated;
    }

    public String getFirstVertexName() {
	return firstVertexName;
    }

    public void setFirstVertexName(String firstVertexName) {
	this.firstVertexName = firstVertexName;
    }

    public String getSecondVertexName() {
	return secondVertexName;
    }

    public void setSecondVertexName(String secondVertexName) {
	this.secondVertexName = secondVertexName;
    }

    public Boolean getIsAssociated() {
	return isAssociated;
    }

    public void setIsAssociated(Boolean isAssociated) {
	this.isAssociated = isAssociated;
    }

    @Override
    public String toString() {
	return this.firstVertexName + " -> " + this.secondVertexName + " : " + (isAssociated ? "Associated" : "Generalized");
    }

    @Override
    public boolean equals(Object obj) {
	Edge edge = (Edge) obj;
	return (this.firstVertexName.equalsIgnoreCase(edge.getFirstVertexName()) && this.secondVertexName.equalsIgnoreCase(edge.getSecondVertexName())) || (this.secondVertexName.equalsIgnoreCase(edge.getFirstVertexName()) && this.firstVertexName.equalsIgnoreCase(edge.getSecondVertexName()));
    }
}
