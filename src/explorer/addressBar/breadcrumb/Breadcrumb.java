package explorer.addressBar.breadcrumb;

import explorer.contentPane.ICatalogTreeModel;
import explorer.contentPane.RootNodeObserver;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * breadcrumb样式的导航栏，单击跳转，使用ComboBox预览下级目录
 */
public class Breadcrumb extends Composite implements RootNodeObserver {
    private IBreadcrumbController controller;
    private ICatalogTreeModel model;
    private ToolBar toolBar;
    private Text siteText;
    private GridData gridData;
    private Color white;

    public Breadcrumb(Composite parent, ICatalogTreeModel model) {
        super(parent, SWT.FLAT);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        setLayout(gridLayout);
        white = new Color(getDisplay(), 255, 255, 255);
        setBackground(white);

        this.model = model;
        model.registerObserver(this);

        toolBar = new ToolBar(this, SWT.DROP_DOWN);
        toolBar.setBackground(white);
        gridData = new GridData();
        toolBar.setLayoutData(gridData);
        siteText = new Text(this, SWT.FLAT);
        siteText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
        siteText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                Breadcrumb.this.textFocusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                Breadcrumb.this.textFocusLost(e);
            }
        });

        siteText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == SWT.CR) {
                    Text text = (Text) e.widget;
                    controller.inputCatalog(text.getText());
                }
            }
        });

        controller = new BreadcrumbController(model, this);
        addAll();

        addDisposeListener(Breadcrumb.this::widgetDisposed);
    }

    private void add(Object node) {
        BreadcrumbItem item = new BreadcrumbItem(this);
        item.setText(model.getNodeName(node));
        item.setNode(node);
        layout();
    }

    private void addAll() {
        removeAll();

        for (Object root : model.getRoots()) {
            add(root);
        }
    }

    private void removeAll() {
        for (ToolItem item : toolBar.getItems()) {
            item.dispose();
        }
    }

    ToolBar getToolBar() {
        return toolBar;
    }

    IBreadcrumbController getController() {
        return controller;
    }

    @Override
    public void updateState() {
        addAll();
    }

    private void widgetDisposed(DisposeEvent e) {
        white.dispose();
    }

    private void textFocusGained(FocusEvent e) {
        Text text = (Text) e.widget;
        // TODO 这里不通用需要改
        String path = (String) model.getRootNode();
        text.setText(path);
        text.selectAll();
        gridData.exclude = true;
        toolBar.setVisible(false);
        layout();
    }

    private void textFocusLost(FocusEvent e) {
        Text text = (Text) e.widget;
        text.setText("");
        gridData.exclude = false;
        toolBar.setVisible(true);
        layout();
    }
}
