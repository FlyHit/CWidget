package explorer.contentPane;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类提供了ICatalogTreeModel接口中一些方法的默认实现，由model类继承
 */
public abstract class CatalogTreeModel implements ICatalogTreeModel {
    protected final Object ROOTNODE;
    protected Object rootNode;
    private List<Object> roots;
    private List<RootNodeObserver> rootNodeObservers;

    public CatalogTreeModel(Object rootNode) {
        this.rootNode = rootNode;
        this.ROOTNODE = rootNode;
        this.roots = new ArrayList<>();
        roots.add(rootNode);
        rootNodeObservers = new ArrayList<>();
    }

    @Override
    public boolean hasChildren(Object element) {
        return rootNode == element;
    }

    @Override
    public Object getRootNode() {
        return rootNode;
    }

    @Override
    public List<Object> getRoots() {
        return roots;
    }

    @Override
    public void setRoots(Object rootNode) {
        if (setRootNode(rootNode)) {
            updateRoots(rootNode);
            notifyObserver();
        }
    }

    /**
     * 设置rootNode，并返回结果
     *
     * @param rootNode 根节点
     * @return 如果成功设置，返回true；反之，false
     */
    protected abstract boolean setRootNode(Object rootNode);

    /**
     * 更新根节点列表。 给定根节点，将其parent一一添加到根节点列表roots，直至ROOTNODE
     *
     * @param element 当前的根节点
     */
    private void updateRoots(Object element) {
        Object parent = getParent(element);

        if (!element.equals(ROOTNODE)) {
            if (!parent.equals(ROOTNODE)) {
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
        if (!rootNode.equals(ROOTNODE)) {
            int lastIndex = roots.size() - 1;

            if (setRootNode(roots.get(lastIndex - 1))) {
                roots.remove(lastIndex);
                notifyObserver();
            }
        }
    }

    @Override
    public void registerObserver(RootNodeObserver observer) {
        rootNodeObservers.add(observer);
    }

    @Override
    public void notifyObserver() {
        for (RootNodeObserver observer : rootNodeObservers) {
            observer.update();
        }
    }

    @Override
    public void open(String itemName) {
        Object selectedNode = findNode(itemName);

        if (setRootNode(selectedNode)) {
            roots.add(selectedNode);
            notifyObserver();
        }
    }

    @Override
    public void handleInput(String input) {
        Object node = convertInput(input);
        setRoots(node);
        notifyObserver();
    }

    protected abstract Object convertInput(String input);

    protected abstract Object findNode(Object name);
}
