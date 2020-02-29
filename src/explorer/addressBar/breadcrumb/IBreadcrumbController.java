package explorer.addressBar.breadcrumb;

import explorer.contentPane.Node;

public interface IBreadcrumbController {
    /**
     * 跳转到目录
     */
    void jumpToCatalog(Node catalog);

    /**
     * 预览目录
     *
     * @param item
     */
    void previewCatalog(Object item);

    void inputCatalog(String catalog);
}
