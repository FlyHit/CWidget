package explorer.contentPane;

import org.eclipse.jface.resource.ImageDescriptor;

public class Node {
    private String name;
    private ImageDescriptor imageDescriptor;
    private Object data;

    public Node(Object node) {
        this.data = node;
        this.name = "";
        this.imageDescriptor = null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageDescriptor getImageDescriptor() {
        return imageDescriptor;
    }

    public void setImageDescriptor(ImageDescriptor imageDescriptor) {
        this.imageDescriptor = imageDescriptor;
    }
}
