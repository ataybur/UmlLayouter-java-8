/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ataybur.umlLayouter.entity;

import com.ataybur.umlLayouter.util.ProjectConstants;

/**
 *
 * @author atay
 */
public class Vertex {

    private String name;
    private Coordinate coordinate;
    private Integer degree;
    private int forIterator;

    public Vertex(String name) {
        this.name = name;
        this.coordinate = new Coordinate();
        degree = 0;
    }

    public Vertex(Vertex vertex) {
        this.name = vertex.getName();
        this.coordinate = new Coordinate(vertex.getCoordinate());
        
        this.degree = vertex.getDegree();
        forIterator = ProjectConstants.VERTEX_COUNTER;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return this.name + ",degree:  " + this.degree + ", Coordinate: " + this.coordinate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
