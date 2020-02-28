package explorer;

import explorer.addressBar.breadcrumb.Breadcrumb;
import explorer.buttonPart.ButtonPart;
import explorer.contentPane.ContentPane;
import explorer.contentPane.ICatalogTreeModel;
import explorer.searchBar.SearchBox;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class Explorer extends Composite {
    private SearchBox searchBox;
    private Breadcrumb breadcrumb;
    private ButtonPart buttonPart;
    private ContentPane contentPane;

    public Explorer(Composite parent, ICatalogTreeModel model) {
        super(parent, SWT.FLAT);
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        setLayout(gridLayout);

        searchBox = new SearchBox(this, SWT.FLAT);
        searchBox.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));

        Composite middleComposite = new Composite(this, SWT.FLAT);
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

        contentPane = new ContentPane(this, model);
        contentPane.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
                true, true));
    }

    public void setContentProvider(ITreeContentProvider contentProvider) {
        contentPane.setContentProvider(contentProvider);
    }

    public void setLabelProvider(ILabelProvider labelProvider) {
        contentPane.setLabelProvider(labelProvider);
    }

    public void refresh() {
        contentPane.refresh();
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
