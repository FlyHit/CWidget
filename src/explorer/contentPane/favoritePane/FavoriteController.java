package explorer.contentPane.favoritePane;

import explorer.contentPane.ContentPane;
import explorer.contentPane.IContentTreeModel;
import explorer.contentPane.Node;
import org.eclipse.nebula.widgets.gallery.GalleryItem;

public class FavoriteController {
    private FavoritePane favoritePane;
    private IContentTreeModel model;

    public FavoriteController(FavoritePane favoritePane, IContentTreeModel model) {
        this.favoritePane = favoritePane;
        this.model = model;
    }

    /**
     * 打开
     */
    void open() {
        GalleryItem[] galleryItems = favoritePane.getGallery().getSelection();

        if (galleryItems.length != 0) {
            GalleryItem item = galleryItems[0];
            Node node = (Node) item.getData("node");
            model.setRoots(node);
            ContentPane contentPane = (ContentPane) favoritePane.getParent().getParent();
            contentPane.switchPage(ContentPane.PAGE.VIEWER);
        }
    }
}
