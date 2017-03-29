<?xml version="1.0" standalone="no" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:UML="org.omg/UML/1.4"
                xmlns:y="http://www.yworks.com/xml/graphml"
                exclude-result-prefixes="UML"
                version="1.0">
    <xsl:output indent="yes"/>
    <xsl:template match="/">
        <graphml xmlns="http://graphml.graphdrawing.org/xmlns"  
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://graphml.graphdrawing.org/xmlns
     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd">
            <xsl:apply-templates />            
        </graphml>
    </xsl:template>
    <xsl:template match="XMI[@xmi.version='1.2']">
        <xsl:apply-templates select="XMI.content/UML:Model"/>
    </xsl:template>    
    <xsl:template match="XMI">
        <xsl:message terminate="yes">Unknown XMI version</xsl:message>
    </xsl:template>  
    <xsl:template match="UML:Stereotype.baseClass">
    </xsl:template>   
    <xsl:template match= "UML:Model">
        <graph id="{@xmi.id}" edgedefault="undirected">
            <xsl:apply-templates />
        </graph>
    </xsl:template>    
    <xsl:template match="UML:Namespace.ownedElement/UML:Class">        
        <node id ="{@xmi.id}" name="{@name}">                       
        </node>
    </xsl:template>    
    <xsl:template match="UML:Association">
        <xsl:variable name="src"> 
            <xsl:value-of select="UML:Association.connection/UML:AssociationEnd[1]/UML:AssociationEnd.participant/UML:Class/@xmi.idref"/> 
        </xsl:variable>
        <xsl:variable name="directed"> 
            <xsl:choose>
                <xsl:when test="UML:Association.connection/UML:AssociationEnd[1]/@isNavigable = 'true'">
                    <xsl:value-of select="'false'"/>  
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'true'"/>  
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>		
        <xsl:variable name="trg"> 
            <xsl:value-of select="UML:Association.connection/UML:AssociationEnd[2]/UML:AssociationEnd.participant/UML:Class/@xmi.idref"/> 
        </xsl:variable>
        <xsl:variable name="source"> 
            <xsl:for-each select="//UML:Class">
                <xsl:if test="@xmi.id = $src">
                    <xsl:value-of select="./@xmi.id"/>                                        
                </xsl:if>  
            </xsl:for-each>
        </xsl:variable>
        <xsl:variable name="target"> 
            <xsl:for-each select="//UML:Class">
                <xsl:if test="@xmi.id = $trg">
                    <xsl:value-of select="./@xmi.id"/>
                </xsl:if>  
            </xsl:for-each>
        </xsl:variable>		
        <edge id="{@xmi.id}" directed="{$directed}" source="{$source}" target="{$target}" />               
    </xsl:template>
              
             
         
</xsl:stylesheet>