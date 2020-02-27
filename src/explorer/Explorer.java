package explorer;

import explorer.addressBar.breadcrumb.Breadcrumb;
import explorer.buttonPart.ButtonPart;
import explorer.contentPane.CatalogTreeViewer;
import explorer.contentPane.ICatalogTreeModel;
import explorer.searchBar.SearchBox;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class Explorer extends Composite {
    private SearchBox searchBox;
    private ButtonPart buttonPart;
    private Composite contentComposite;
    private Composite middleComposite;
    private Breadcrumb breadcrumb;
    private CatalogTreeViewer viewer;

    public Explorer(Composite parent, ICatalogTreeModel model) {
        super(parent, SWT.FLAT);
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        setLayout(gridLayout);

        searchBox = new SearchBox(this, SWT.FLAT);
        searchBox.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));

        middleComposite = new Composite(this, SWT.FLAT);
        middleComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        GridLayout gridLayout1 = new GridLayout();
        gridLayout1.numColumns = 2;
        gridLayout1.marginHeight = 0;
        gridLayout1.marginWidth = 0;
        middleComposite.setLayout(gridLayout1);
        buttonPart = new ButtonPart(middleComposite, model);
        buttonPart.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, true));
        breadcrumb = new Breadcrumb(middleComposite, model);
        breadcrumb.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

        contentComposite = new Composite(this, SWT.FLAT);
        contentComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
        contentComposite.setLayout(new FillLayout());
        viewer = new CatalogTreeViewer(contentComposite, model);
    }

    public void setContentProvider(ITreeContentProvider contentProvider) {
        viewer.setContentProvider(contentProvider);
    }

    public void setLabelProvider(ILabelProvider labelProvider) {
        viewer.setLabelProvider(labelProvider);
    }

    public void refresh() {
        viewer.setInput();
    }

    public void setBackImage(Image image) {
        buttonPart.setBackImage(image);
    }

    public void setSearchImage(Image image) {
        searchBox.setSearchImage(image);
    }

    public void setFavoriteImage(Image image) {
        buttonPart.setFavoriteImage(image);
    }
}
