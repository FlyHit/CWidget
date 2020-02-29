package explorer.contentPane.favoritePane;

import explorer.contentPane.ContentPane;
import explorer.contentPane.ICatalogTreeModel;
import explorer.contentPane.Node;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.nebula.widgets.gallery.NoGroupRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import java.util.List;

/**
 * 显示收藏内容的面板
 */
public class FavoritePane extends Composite implements FavoriteListObserver {
    private Gallery gallery;
    private GalleryItem galleryGroup;
    private ICatalogTreeModel model;

    public FavoritePane(Composite parent, ICatalogTreeModel model) {
        super(parent, SWT.FLAT);
        setLayout(new FillLayout());
        this.model = model;

        gallery = new Gallery(this, SWT.SINGLE | SWT.V_SCROLL);
        NoGroupRenderer noGroupRenderer = new NoGroupRenderer();
        noGroupRenderer.setItemSize(64, 64);
        gallery.setGroupRenderer(noGroupRenderer);
        gallery.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                GalleryItem[] galleryItems = gallery.getSelection();
                if (galleryItems.length != 0) {
                    GalleryItem item = galleryItems[0];
                    Node node = (Node) item.getData("node");
                    model.setRoots(node);
                    ContentPane contentPane = (ContentPane) parent.getParent();
                    contentPane.switchPage(ContentPane.PAGE.VIEWER);
                }
            }
        });
        galleryGroup = new GalleryItem(gallery, SWT.NONE);
    }

    public void add(GalleryItem item, Object node) {
        GalleryItem galleryItem = new GalleryItem(galleryGroup, SWT.NONE);
        galleryItem.setText(item.getText());
        galleryItem.setImage(item.getImage());
        galleryItem.setData("node", node);
        gallery.layout();
    }

    public GalleryItem[] getItems() {
        return galleryGroup.getItems();
    }

    @Override
    public void updateState() {
        List favoriteList = model.getFavoriteList();
    }
}
