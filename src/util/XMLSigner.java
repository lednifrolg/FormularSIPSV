package util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Nolofinwe on 10/22/2016.
 */
public class XMLSigner {

    String mOriginalXMLFile;
    String mSchemaFile;
    String mXSLFile;

    String mOriginalXMLFileContent;
    String mSchemaFileContent;
    String mXSLFileContent;

    String mNamespace;

    ActiveXComponent mXadesSigner;
    ActiveXComponent mXMLPlugin;

    public XMLSigner(String originalXMLFile, String schemaFile, String XSLFile, String namespace) {
        this.mOriginalXMLFile = new File(originalXMLFile).getName();
        this.mSchemaFile = new File(schemaFile).getName();
        this.mXSLFile = new File(XSLFile).getName();

        this.mNamespace = namespace;

        try {
            this.mOriginalXMLFileContent = new Scanner(new File(originalXMLFile)).useDelimiter("\\Z").next();
            this.mSchemaFileContent = new Scanner(new File(schemaFile)).useDelimiter("\\Z").next();
            this.mXSLFileContent = new Scanner(new File(XSLFile)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mXadesSigner = new ActiveXComponent("DSig.XadesSig");
        mXMLPlugin = new ActiveXComponent("DSig.XmlPlugin");
    }


    public void sign() {
        System.out.println(mOriginalXMLFileContent);

        Variant xmlObj = Dispatch.call(mXMLPlugin, "CreateObject", "id1", "catalog",
                mOriginalXMLFileContent, mSchemaFileContent, "", "http://stark.guru/countries.xsd", mXSLFileContent, "http://stark.guru/countries.xsd");
        System.out.println(mXadesSigner.getProperty("ErrorMessage").getString());


        Dispatch.call(mXadesSigner, "AddObject", xmlObj);
        System.out.println(mXadesSigner.getProperty("ErrorMessage").getString());

        Dispatch.call(mXadesSigner, "Sign", "signatureId", "sha256", "urn:oid:1.3.158.36061701.1.2.1");
        String sign = mXadesSigner.getProperty("SignedXmlWithEnvelope").toString();

        System.out.println(mXadesSigner.getProperty("ErrorMessage").getString());

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("sign.xml"));
            out.write(sign);
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}


