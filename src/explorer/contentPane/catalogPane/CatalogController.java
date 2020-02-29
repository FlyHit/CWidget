package explorer.contentPane.catalogPane;

import explorer.contentPane.IContentTreeModel;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.nebula.widgets.gallery.GalleryItem;

import java.util.Optional;

public class CatalogController implements ICatalogController {
    private CatalogPane catalogPane;
    private IContentTreeModel model;

    public CatalogController(CatalogPane catalogPane, IContentTreeModel model) {
        this.catalogPane = catalogPane;
        this.model = model;
    }

    @Override
    public void open(GalleryItem galleryItem) {
        String itemName = galleryItem.getText();
        model.open(itemName);
    }

    @Override
    public IContributionItem[] createMenuItem() {
        IContributionItem addToFavorite = new ActionContributionItem(
                new AddToFavorite());
        IContributionItem viewAction = new ActionContributionItem(
                new ViewAction());
        return new IContributionItem[]{addToFavorite, viewAction};
    }

    class AddToFavorite extends Action {
        @Override
        public void run() {
            Optional.ofNullable(catalogPane.getSelectionItem()).ifPresent(item -> {
                model.addToFavorite(item.getText(0));
            });
        }

        AddToFavorite() {
            setText("添加到收藏");
        }
    }

    class ViewAction extends Action {
        ViewAction() {
            setText("查看");
        }
    }
}
