package test.explorer.xml;

import explorer.contentPane.ContentTreeModel;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FXMLTreeModel extends ContentTreeModel {

	public FXMLTreeModel(Object rootNode) {
		super(rootNode);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Node) {
			Node node = (Node) parentElement;
			int length = node.getChildNodes().getLength();
			Node[] childNodes = new Node[node.getChildNodes().getLength()];

			for (int i = 0; i < length; i++) {
				childNodes[i] = node.getChildNodes().item(i);
			}

			return childNodes;
		}

		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Node) {
			Node node = (Node) element;
			return node.getParentNode();
		}
		return null;
	}

	@Override
	public String getNodeName(Object node) {
		return ((Node) node).getNodeName();
	}

	@Override
	protected boolean setRootNode(Object rootNode) {
		if (rootNode instanceof Node) {
			Node node = (Node) rootNode;
			if (node.hasChildNodes()) {
				this.rootNode = node;
				return true;
			}
		} else {
			System.out.println("设置失败");
		}

		return false;
	}

	@Override
	protected Object convertInput(String input) {
		return null;
	}

	@Override
	public Object findNode(Object name) {
		Node rootNode1 = (Node) rootNode;
		NodeList list = rootNode1.getChildNodes();
		int length = list.getLength();

		for (int i = 0; i < length; i++) {
			if (list.item(i).getNodeName().equals(name)) {
				return list.item(i);
			}
		}

		return null;
	}
}