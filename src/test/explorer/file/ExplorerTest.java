package test.explorer.file;

import explorer.Explorer;
import explorer.contentPane.CatalogTreeContentProvider;
import explorer.contentPane.IContentTreeModel;
import explorer.contentPane.Node;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ExplorerTest {

    protected Shell shell;

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            ExplorerTest window = new ExplorerTest();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
        shell.setSize(450, 300);
        shell.setText("SWT Application");
        shell.setLayout(new FillLayout());

        Node node = new Node("eclipse", "C:\\Program Files\\eclipse rcp\\eclipse201912\\eclipse");
        IContentTreeModel model = new FileTreeModel(node);
        Explorer explorer = new Explorer(shell, model);
        explorer.setContentProvider(new CatalogTreeContentProvider());
        explorer.setLabelProvider(new CatalogLabelProvider());
        explorer.refresh();
    }

}
