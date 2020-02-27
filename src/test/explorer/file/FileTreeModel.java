package test.explorer.file;

import explorer.contentPane.CatalogTreeModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileTreeModel extends CatalogTreeModel {

	public FileTreeModel() {
		super("C:\\Program Files\\eclipse rcp\\eclipse201912\\eclipse");
	}

	@Override
	protected boolean setRootNode(Object rootNode) {
		if (rootNode instanceof String) {
			File file = new File((String) rootNode);
			if (file.exists() && file.isDirectory()) {
				this.rootNode = rootNode;

				return true;
			} else {
				// Todo 抛出异常会不会更好
				System.out.println("非有效文件根路径");
			}
		} else {
			System.out.println("设置失败，rootNode必须是String类型！");
		}

		return false;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		List<String> childList = new ArrayList<>();
		File file = new File((String) parentElement);

		for (File f : Objects.requireNonNull(file.listFiles())) {
			childList.add(f.getPath());
		}

		return childList.toArray();
	}

	@Override
	public Object getParent(Object element) {
		// 最底层目录没有parent
		if (!element.equals(ROOTNODE) && isFilePath(element)) {
			return new File((String) element).getParent();
		}

		return null;
	}

	@Override
	public String getNodeName(Object node) {
		String itemName = "";
		String path = (String) node;
		int lastIndex = path.length() - 1;

		for (int i = lastIndex; i > 0; i--) {
			if (path.charAt(i) == '\\') {
				// subString的结果不包含endIndex处的字符
				itemName = path.substring(i + 1, lastIndex + 1);
				break;
			}
		}

		return itemName;
	}

	private boolean isFilePath(Object element) {
		if (element instanceof String) {
			String path = (String) element;
			File file = new File(path);
			return file.exists();
		}

		return false;
	}

	@Override
	protected Object convertInput(String input) {
		String pathname = input;

		if (pathname.endsWith("\\")) {
			pathname = pathname.substring(0, pathname.length() - 1);
		}

		return pathname;
	}

	@Override
	protected Object findNode(Object name) {
		return getRootNode() + "\\" + name;
	}
}
