package explorer.contentPane;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.PageBook;

public class ContentPane extends Composite {
    private PageBook pageBook;
    private CatalogPane catalogPane;
    private FavoritePane favoritePane;
    private Composite searchComposite;
    private ICatalogTreeModel model;

    public ContentPane(Composite parent, ICatalogTreeModel model) {
        super(parent, SWT.FLAT);
        this.model = model;
        setData("name", "contentPane");
        setLayout(new FillLayout());

        pageBook = new PageBook(this, SWT.FLAT);
        catalogPane = new CatalogPane(pageBook, model);
        favoritePane = new FavoritePane(pageBook, model);
        searchComposite = new Composite(pageBook, SWT.FLAT);
        pageBook.showPage(catalogPane);

        createContextMenu();
    }

    /**
     * 切换当前显示的页面
     *
     * @param page 需要显示的页面
     */
    public void switchPage(PAGE page) {
        Control control = null;
        switch (page) {
            case FAVORITE:
                control = favoritePane;
                break;
            case VIEWER:
                control = catalogPane;
                break;
            case SEARCH:
                control = searchComposite;
                break;
        }

        pageBook.showPage(control);
    }

    private void createContextMenu() {
        MenuManager contextMenu = new MenuManager("#ViewerMenu"); //$NON-NLS-1$
        contextMenu.setRemoveAllWhenShown(true);
        contextMenu.addMenuListener(this::fillContextMenu);

        Menu menu = contextMenu.createContextMenu(catalogPane.getViewer().getControl());
        catalogPane.getViewer().getControl().setMenu(menu);
    }

    private void fillContextMenu(IMenuManager contextMenu) {
        contextMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

        if (catalogPane.getSelectionItem() != null) {
            contextMenu.add(new Action("添加到收藏") {
                @Override
                public void run() {
                    GalleryItem item = catalogPane.getSelectionItem();
                    Object node = model.findNode(item.getText(0));
                    favoritePane.add(item, node);
                }
            });
        }

        // TODO 大图标小图标设置
        contextMenu.add(new Action("查看") {
            @Override
            public void run() {
            }
        });
    }

    public FavoritePane getFavoritePane() {
        return favoritePane;
    }

    public enum PAGE {
        FAVORITE, VIEWER, SEARCH
    }

    public void setContentProvider(ITreeContentProvider contentProvider) {
        catalogPane.setContentProvider(contentProvider);
    }

    public void setLabelProvider(ILabelProvider labelProvider) {
        catalogPane.setLabelProvider(labelProvider);
    }

    public void refresh() {
        catalogPane.updateState();
    }
}
