package explorer.contentPane;

import explorer.contentPane.favoritePane.FavoriteListObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类提供了ICatalogTreeModel接口中一些方法的默认实现，由model类继承
 */
public abstract class ContentTreeModel implements IContentTreeModel {
	protected final Node ROOTNODE;
	protected Node rootNode;
	private List<Node> roots;
	private List<RootNodeObserver> rootNodeObservers;
	protected List<Node> favoriteList;
	private List<FavoriteListObserver> favoriteObservers;

	public ContentTreeModel(Node rootNode) {
		this.rootNode = rootNode;
		this.ROOTNODE = rootNode;
		this.roots = new ArrayList<>();
		this.favoriteList = new ArrayList<>();
		this.favoriteObservers = new ArrayList<>();
		roots.add(rootNode);
		rootNodeObservers = new ArrayList<>();
	}

	@Override
	public boolean hasChildren(Node element) {
		return rootNode == element;
	}

	@Override
	public Node getRootNode() {
		return rootNode;
	}

	@Override
	public List<Node> getRoots() {
		return roots;
	}

	@Override
	public void setRoots(Node rootNode) {
		if (setRootNode(rootNode)) {
			updateRoots(rootNode);
			notifyRootNodeObserver();
		}
	}

	/**
	 * 设置rootNode，并返回结果
	 *
	 * @param rootNode 根节点
	 * @return 如果成功设置，返回true；反之，false
	 */
	protected abstract boolean setRootNode(Node rootNode);

	/**
	 * 更新根节点列表。 给定根节点，将其parent一一添加到根节点列表roots，直至ROOTNODE
	 *
	 * @param element 当前的根节点
	 */
	private void updateRoots(Node element) {
		Node parent = getParent(element);

		if (!element.getData().equals(ROOTNODE.getData())) {
			if (!parent.getData().equals(ROOTNODE.getData())) {
				updateRoots(parent);
			} else {
				roots = new ArrayList<>();
				roots.add(parent);
			}
		} else {
			roots = new ArrayList<>();
		}

		roots.add(element);
	}

	@Override
	public void back() {
		if (!rootNode.getData().equals(ROOTNODE.getData())) {
			int lastIndex = roots.size() - 1;

			if (setRootNode(roots.get(lastIndex - 1))) {
				roots.remove(lastIndex);
				notifyRootNodeObserver();
			}
		}
	}

	@Override
	public void registerRootNodeObserver(RootNodeObserver observer) {
		rootNodeObservers.add(observer);
	}

	private void notifyRootNodeObserver() {
		for (RootNodeObserver observer : rootNodeObservers) {
			observer.updateState();
		}
	}

	@Override
	public void open(String itemName) {
		Node selectedNode = findNode(itemName);

		if (setRootNode(selectedNode)) {
			roots.add(selectedNode);
			notifyRootNodeObserver();
		}
	}

	@Override
	public void handleInput(String input) {
		Node node = convertInput(input);
		setRoots(node);
		notifyRootNodeObserver();
	}

	protected abstract Node convertInput(String input);

	private Node findNode(String itemName) {
		for (Node child : getChildren(rootNode)) {
			if (child.getName().equals(itemName)) {
				return child;
			}
		}

		return null;
	}

	@Override
	public void addToFavorite(String itemName) {
		Node node = findNode(itemName);
		notifyFavoriteObservers(true, node);
	}

	@Override
	public void removeFromFavorite(String itemName) {
		Node node = findNode(itemName);
		notifyFavoriteObservers(false, node);
	}

	private void notifyFavoriteObservers(boolean isAdd, Node node) {
		for (FavoriteListObserver favoriteObserver : favoriteObservers) {
			favoriteObserver.updateState(isAdd, node);
		}
	}

	@Override
	public List<Node> getFavoriteList() {
		return favoriteList;
	}

	@Override
	public void registerFavoriteObserver(FavoriteListObserver observer) {
		favoriteObservers.add(observer);
	}
}
