package explorer.contentPane;

import org.eclipse.nebula.widgets.gallery.GalleryItem;

public class CatalogController implements ICatalogController {
    private CatalogPane catalogPane;
    private ICatalogTreeModel model;

    public CatalogController(CatalogPane catalogPane, ICatalogTreeModel model) {
        this.catalogPane = catalogPane;
        this.model = model;
    }

    @Override
    public void open(GalleryItem galleryItem) {
        String itemName = galleryItem.getText();
        model.open(itemName);
    }
}
