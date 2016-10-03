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
                        <tr>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="price"/></td>

                            <xsl:for-each select="variant">

                                <td><xsl:value-of select="size" /></td>
                                <td><xsl:value-of select="available" /></td>

                            </xsl:for-each>

                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>

