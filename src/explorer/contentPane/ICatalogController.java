package explorer.contentPane;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.nebula.widgets.gallery.GalleryItem;

public interface ICatalogController {
    /**
     * 打开文件夹/文件
     *
     * @param galleryItem
     */
    void open(GalleryItem galleryItem);

    /**
     * 创建菜单项
     *
     * @return 菜单项
     */
    IContributionItem[] createMenuItem();
}
