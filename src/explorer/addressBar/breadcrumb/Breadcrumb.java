package explorer.addressBar.breadcrumb;

import explorer.contentPane.ICatalogTreeModel;
import explorer.contentPane.RootNodeObserver;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
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

    public Breadcrumb(Composite parent, ICatalogTreeModel model) {
        super(parent, SWT.FLAT);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        setLayout(gridLayout);

        this.model = model;
        model.registerObserver(this);

        toolBar = new ToolBar(this, SWT.DROP_DOWN);
        gridData = new GridData();
        toolBar.setLayoutData(gridData);
        siteText = new Text(this, SWT.FLAT);
        siteText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
        siteText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                Text text = (Text) e.widget;
                String path = (String) model.getRootNode();
//                text.setText(path.substring(FileTreeModel.BASEPATH.length()));
                text.setText(path);
                text.selectAll();
                gridData.exclude = true;
                toolBar.setVisible(false);
                layout();
            }

            @Override
            public void focusLost(FocusEvent e) {
                Text text = (Text) e.widget;
                text.setText("");
                gridData.exclude = false;
                toolBar.setVisible(true);
                layout();
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

    public void remove() {
        int lastItemIndex = toolBar.getItemCount() - 1;
        toolBar.getItem(lastItemIndex).dispose();
    }

    public void removeAll() {
        for (ToolItem item : toolBar.getItems()) {
            item.dispose();
        }
    }

    ToolBar getToolBar() {
        return toolBar;
    }

    Text getSiteText() {
        return siteText;
    }

    IBreadcrumbController getController() {
        return controller;
    }

    @Override
    public void update() {
        addAll();
    }
}
