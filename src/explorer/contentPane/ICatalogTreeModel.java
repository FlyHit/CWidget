package explorer.contentPane;

import explorer.contentPane.favoritePane.FavoriteListObserver;

import java.util.List;

/**
 * 所有显示在Explorer上的model必须实现该接口
 */
public interface ICatalogTreeModel {
    Node[] getChildren(Node parentElement);

    Node getParent(Node element);

    boolean hasChildren(Node element);

    Node getRootNode();

    /**
     * @return 父节点列表（父节点，父父节点...)
     */
    List<Node> getRoots();

    void setRoots(Node rootNode);

    void registerRootNodeObserver(RootNodeObserver observer);

    void registerFavoriteObserver(FavoriteListObserver observer);

    void open(String itemName);

    /**
     * 返回上级目录
     * @return 如果已经是最上级目录，返回false；反之，true
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
    String getNodeName(Node node);

    Node findNode(Object name);

    /**
     * 添加到收藏
     *
     * @param itemName 添加项目的名称
     */
    void addToFavorite(String itemName);

    /**
     * 移除收藏的项目
     *
     * @param itemName 收藏项目的名称
     */
    void removeFromFavorite(String itemName);

    List<Node> getFavoriteList();
}
