package explorer.buttonPart;

import explorer.contentPane.ContentPane;
import explorer.contentPane.FavoritePane;
import explorer.contentPane.ICatalogTreeModel;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

class ButtonPartController {
    private ICatalogTreeModel model;
    private ButtonPart buttonPart;
    private ContentPane contentPane;
    private boolean isFavorite;

    ButtonPartController(ButtonPart buttonPart, ICatalogTreeModel model) {
        this.buttonPart = buttonPart;
        this.model = model;
    }

    /**
     * 后退
     */
    void back() {
        if (isFavorite) {
            contentPane.switchPage(ContentPane.PAGE.VIEWER);
            isFavorite = false;
            model.setRoots(model.getRootNode());
        } else {
            model.back();
        }

        buttonPart.setFocus();
    }

    /**
     * 跳转到收藏文件夹（目录）
     */
    void jumpToFavorite() {
        setContentPane();
        contentPane.switchPage(ContentPane.PAGE.FAVORITE);
        isFavorite = true;
        buttonPart.setFocus();
    }

    /**
     * 预览收藏的项目
     */
    void previewFavorite(ToolItem toolItem) {
        setContentPane();
        FavoritePane favoritePane = contentPane.getFavoritePane();
        GalleryItem[] items = favoritePane.getItems();

        final Menu menu = new Menu(contentPane.getShell(), SWT.POP_UP);

        for (GalleryItem item : items) {
            String menuItemName = item.getText();
            MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
            menuItem.setText(menuItemName);
            menuItem.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    model.setRoots(item.getData("node"));
                }
            });
        }

        Rectangle rect = toolItem.getBounds();
        Point pt = new Point(rect.x, rect.y + rect.height);
        pt = toolItem.getParent().toDisplay(pt);
        menu.setLocation(pt.x, pt.y);
        menu.setVisible(true);
    }

    /**
     * 获取contentPane,并保存一份引用
     */
    private void setContentPane() {
        if (contentPane == null) {
            Composite explorer = buttonPart.getParent().getParent();
            for (Control child : explorer.getChildren()) {
                Object name = child.getData("name");
                if (name != null && name.equals("contentPane")) {
                    this.contentPane = (ContentPane) child;
                }
            }
        }
    }
}
