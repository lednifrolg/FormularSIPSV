<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Size</th>
                        <th>Available</th>
                    </tr>
                    <xsl:for-each select="catalog/product">
                        <xsl:variable name="productName" select="name"/>
                        <xsl:variable name="productPrice" select="price"/>
                        <xsl:for-each select="variant">
                            <tr>
                                <td><xsl:value-of select="$productName"/> </td>
                                <td><xsl:value-of select="$productPrice"/></td>
                                <td><xsl:value-of select="size" /></td>
                                <td><xsl:value-of select="available" /></td>
                            </tr>
                        </xsl:for-each>

                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>

