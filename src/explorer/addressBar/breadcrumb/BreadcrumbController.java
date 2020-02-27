package explorer.addressBar.breadcrumb;

import explorer.contentPane.ICatalogTreeModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;

public class BreadcrumbController implements IBreadcrumbController {
    private ICatalogTreeModel model;
    private Breadcrumb breadcrumb;

    public BreadcrumbController(ICatalogTreeModel model, Breadcrumb breadcrumb) {
        this.model = model;
        this.breadcrumb = breadcrumb;
    }

    @Override
    public void jumpToCatalog(Object catalog) {
        model.setRoots(catalog);
    }

    @Override
    public void previewCatalog(Object item) {
        ToolItem toolItem = (ToolItem) item;

        final Menu menu = new Menu(toolItem.getParent().getShell(), SWT.POP_UP);

        for (Object p : model.getChildren(toolItem.getData("node"))) {
            String menuItemName = model.getNodeName(p);
            MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
            menuItem.setText(menuItemName);
            menuItem.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    jumpToCatalog(p);
                }
            });
        }

        Rectangle rect = toolItem.getBounds();
        Point pt = new Point(rect.x, rect.y + rect.height);
        pt = toolItem.getParent().toDisplay(pt);
        menu.setLocation(pt.x, pt.y);
        menu.setVisible(true);
    }

    @Override
    public void inputCatalog(String catalog) {
        model.handleInput(catalog);
        // 使siteText lost focus, setFocus()不管用
        breadcrumb.forceFocus();
    }
}
