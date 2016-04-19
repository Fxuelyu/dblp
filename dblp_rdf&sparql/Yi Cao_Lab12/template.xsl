<?xml version="1.0" encoding="UTF-8" standalone="yes" ?>	
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:html="http://www.w3.org/1999/xhtml"  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
 xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:foaf="http://xmlns.com/foaf/spec/" xmlns:foo="http://www.me.org/dblp/">
 <xsl:variable name="header">
    ;
</xsl:variable>
  <xsl:template match="/">
  <rdf:RDF>
   <xsl:for-each select="dblp/article">
   <xsl:variable name="counter" select="position()"> </xsl:variable>
      <rdf:Description rdf:about="http://www.me.org/dblp/article{$counter}">
     <foo:ee>
       <xsl:value-of select="ee"/>
    </foo:ee>
    <foo:url>
       <xsl:value-of select="url"/>
    </foo:url>
    <foo:number>
       <xsl:value-of select="number"/>
    </foo:number>
    <foo:journal>
       <xsl:value-of select="journal"/>
    </foo:journal>
    <foo:volume>
       <xsl:value-of select="volume"/>
    </foo:volume>
    <foo:year>
       <xsl:value-of select="year"/>
    </foo:year>
    <foo:pages>
       <xsl:value-of select="pages"/>
    </foo:pages>
    <foo:title>
       <xsl:value-of select="title"/>
    </foo:title>
    
      <foo:author>
        <xsl:for-each select="author">
          <xsl:value-of select="."/>
      <xsl:copy-of select="$header" />
        </xsl:for-each>
       </foo:author>
      </rdf:Description>
    </xsl:for-each>
    </rdf:RDF>
  </xsl:template>

</xsl:stylesheet>