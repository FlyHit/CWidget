package explorer.contentPane;

import org.eclipse.jface.viewers.ITreeContentProvider;

public class CatalogTreeContentProvider implements ITreeContentProvider {
    private ICatalogTreeModel model;

    @Override
    public Object[] getElements(Object inputElement) {
        this.model = (ICatalogTreeModel) inputElement;
        Object rootNode = model.getRootNode();

        return new Object[]{rootNode};
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        return model.getChildren(parentElement);
    }

    @Override
    public Object getParent(Object element) {
        return model.getParent(element);
    }

    @Override
    public boolean hasChildren(Object element) {
        return model.hasChildren(element);
    }
}
