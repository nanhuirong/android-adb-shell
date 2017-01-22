package com.panglei;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Huirong on 17/1/22.
 */
public class ParseXML {
    public static String getApp(String fileName)throws Exception {
//        File file = new File(fileName);
//        System.out.println(file.exists());
//        System.out.println(fileName)
        System.out.println("---------");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(fileName);
        NodeList nodeList = document.getChildNodes();
        String appName = null;
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            NodeList hierarchy = node.getChildNodes();
//            System.out.println(node.getNodeName());
            for (int j = 0; j < hierarchy.getLength(); j++){
                Node hierarchyNode = hierarchy.item(j);
                NamedNodeMap map = hierarchyNode.getAttributes();
                Node nodePackage = map.getNamedItem("package");
                appName = nodePackage.getNodeValue();
            }
        }
        return appName;
    }
}
