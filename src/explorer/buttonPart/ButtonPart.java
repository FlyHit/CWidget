package explorer.buttonPart;

import explorer.contentPane.ICatalogTreeModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * 地址栏旁边的按钮部分：返回按钮+收藏按钮
 */
public class ButtonPart {
    private ButtonPartController controller;
    private ToolBar toolBar;
    private ToolItem backButton;
    private ToolItem favoriteButton;

    public ButtonPart(Composite parent, ICatalogTreeModel model) {
        toolBar = new ToolBar(parent, SWT.FLAT);
        backButton = new ToolItem(toolBar, SWT.FLAT);
        backButton.setText("◀");
        favoriteButton = new ToolItem(toolBar, SWT.DROP_DOWN);
        favoriteButton.setText("★");
        controller = new ButtonPartController(this, model);

        addListener();
    }

    private void addListener() {
        backButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                controller.back();
            }
        });

        favoriteButton.addListener(SWT.Selection, event -> {
            if (event.detail == SWT.ARROW) {
                controller.previewCatalog();
            } else {
                controller.jumpToCatalog();
            }
        });
    }

    public boolean setFocus() {
        return toolBar.setFocus();
    }

    public void setLayoutData(Object layoutData) {
        toolBar.setLayoutData(layoutData);
    }

    public void setBackImage(Image image) {
        backButton.setImage(image);
    }

    public void setFavoriteImage(Image image) {
        favoriteButton.setImage(image);
    }
}
