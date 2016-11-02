<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:egonp="http://schemas.fiit.sk/form">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    <xsl:template match="/">
        <html>
            <body>
                <table border="1">
                    <tr bgcolor="#ff0000">
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Size</th>
                        <th>Available</th>
                    </tr>
                    <xsl:for-each select="/egonp:catalog/egonp:product">
                        <xsl:variable name="productName" select="egonp:name"/>
                        <xsl:variable name="productPrice" select="egonp:price"/>
                        <xsl:for-each select="egonp:variant">
                            <tr>
                                <td><xsl:value-of select="$productName"/> </td>
                                <td><xsl:value-of select="$productPrice"/></td>
                                <td>
                                    <xsl:value-of select="egonp:size"/>
                                </td>
                                <td>
                                    <xsl:value-of select="egonp:available"/>
                                </td>
                            </tr>
                        </xsl:for-each>

                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>

