package test.explorer.xml;

import explorer.Explorer;
import explorer.contentPane.CatalogTreeContentProvider;
import explorer.contentPane.ICatalogTreeModel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ExplorerTest {

	protected Shell shell;

	/**
	 * Launch the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ExplorerTest window = new ExplorerTest();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() throws IOException, SAXException, ParserConfigurationException {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() throws ParserConfigurationException, IOException, SAXException {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout());

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File("RibbonCompositeFXML.fxml"));

		NodeList nodeList = doc.getChildNodes();
		Node root = doc.getFirstChild();
		System.out.println(root + "1w");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeName().equals("BorderPane")) {
				Node node1 = node.getChildNodes().item(1);
				root = node1.getChildNodes().item(1);
				System.out.println(root);
			}
		}

		ICatalogTreeModel model = new FXMLTreeModel(root);
		Explorer explorer = new Explorer(shell, model);
		explorer.setContentProvider(new CatalogTreeContentProvider());
		explorer.setLabelProvider(new FXMLLabelProvider());
		explorer.refresh();
	}

}
