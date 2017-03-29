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
    <xsl:template match="UML:Namespace.ownedElement/UML:Class | UML:Namespace.ownedElement/UML:Interface">        
        <node id ="{@name}" name="{@name}">                       
        </node>
    </xsl:template>    
           
              
    <xsl:template match="UML:Abstraction">
        <xsl:variable name="stereotype"> 
            <xsl:value-of select="UML:ModelElement.stereotype/UML:Stereotype/@xmi.idref"/> 
        </xsl:variable>
        <xsl:variable name="src"> 
            <xsl:value-of select="UML:Dependency.client/UML:Class/@xmi.idref"/> 
        </xsl:variable>
        <xsl:variable name="trg"> 
            <xsl:value-of select="UML:Dependency.supplier/UML:Interface/@xmi.idref"/> 
        </xsl:variable>
        <xsl:variable name="source"> 
            <xsl:for-each select="//UML:Class">
                <xsl:if test="@xmi.id = $src">
                    <xsl:value-of select="./@name"/>                                        
                </xsl:if>  
            </xsl:for-each>
        </xsl:variable>
        <xsl:variable name="target"> 
            <xsl:for-each select="//UML:Interface">
                <xsl:if test="@xmi.id = $trg">
                    <xsl:value-of select="./@name"/>
                </xsl:if>  
            </xsl:for-each>
        </xsl:variable>		
        <xsl:variable name="directed"> 
            <xsl:for-each select="../UML:Stereotype">
                <xsl:choose>
                    <xsl:when test="@xmi.id = $stereotype">
                        <xsl:value-of select="'true'"/>  
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'false'"/>  
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
        </xsl:variable>		
        <edge id="{@xmi.id}" directed="{$directed}" source="{$source}" target="{$target}" />               
    </xsl:template>
    
    
       <xsl:template match="UML:Generalization">
        <xsl:variable name="src"> 
            <xsl:value-of select="UML:Generalization.child/UML:Class/@xmi.idref"/> 
        </xsl:variable>
	
        <xsl:variable name="trg"> 
            <xsl:value-of select="UML:Generalization.parent/UML:Class/@xmi.idref"/> 
        </xsl:variable>
        <xsl:variable name="source"> 
            <xsl:for-each select="//UML:Class">
                <xsl:if test="@xmi.id = $src">
                    <xsl:value-of select="./@name"/>                                        
                </xsl:if>  
            </xsl:for-each>
        </xsl:variable>
        <xsl:variable name="target"> 
            <xsl:for-each select="//UML:Class">
                <xsl:if test="@xmi.id = $trg">
                    <xsl:value-of select="./@name"/>
                </xsl:if>  
            </xsl:for-each>
        </xsl:variable>		
        <edge id="{@xmi.id}" directed="true" source="{$source}" target="{$target}" />               
    </xsl:template>
</xsl:stylesheet>