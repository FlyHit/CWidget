package test.explorer.file;

import explorer.contentPane.Node;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import java.io.File;

public class CatalogLabelProvider implements ILabelProvider {
    @Override
    public Image getImage(Object element) {
        return null;
    }

    @Override
    public String getText(Object element) {
        String text = "";

        if (element instanceof Node) {
            Node node = (Node) element;
            File file = new File((String) node.getData());
            text = file.getName();

            if (text.length() == 0) {
                String path = file.getPath();
                // 减去末尾的\，如C:\
                text = path.substring(0, path.length() - 1);
            }
        }

        return text;
    }

    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
    }
}
