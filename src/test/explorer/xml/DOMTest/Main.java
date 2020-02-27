package test.explorer.xml.DOMTest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File("RibbonCompositeFXML.fxml"));

		visit(doc, 0);
	}

	public static void visit(Node node, int level) {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node childNode = list.item(i);
//			if (childNode.getNodeName().equals("RibbonTab")) {
//				System.out.println(childNode.getAttributes().
//						getNamedItem("text").getNodeValue());
//			}
			System.out.println(childNode.getNodeName());

//            visit(childNode, level + 1);
		}
	}
}
