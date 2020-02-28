package explorer.contentPane;

import java.util.List;

/**
 * 所有显示在Explorer上的model必须实现该接口
 *
 * @param <T>
 */
public interface ICatalogTreeModel<T> {
    Object[] getChildren(Object parentElement);

    Object getParent(Object element);

    boolean hasChildren(Object element);

    Object getRootNode();

    /**
     * @return 父节点列表（父节点，父父节点...)
     */
    List<T> getRoots();

    void setRoots(Object rootNode);

    void registerObserver(RootNodeObserver observer);

    void notifyObserver();

    void open(String itemName);

    /**
     * 返回上级目录
     */
    void back();

    /**
     * 处理输入的地址
     *
     * @param input 输入的地址
     */
    void handleInput(String input);

    /**
     * 获取给定节点的名称，用于显示在breadcrumb上
     *
     * @param node 给定的节点
     * @return 给定节点的名称
     */
    String getNodeName(Object node);

    Object findNode(Object name);
}
