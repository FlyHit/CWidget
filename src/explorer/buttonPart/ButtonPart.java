package explorer.buttonPart;

import explorer.contentPane.ICatalogTreeModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * 地址栏旁边的按钮部分：返回按钮+收藏按钮
 */
public class ButtonPart extends Composite {
	private ButtonPartController controller;
	private ToolBar toolBar;
	private ToolItem backButton;
	private ToolItem favoriteButton;

	public ButtonPart(Composite parent, ICatalogTreeModel model) {
		super(parent, SWT.FLAT);
		setLayout(new FillLayout());
		toolBar = new ToolBar(this, SWT.FLAT);
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
				controller.previewFavorite(favoriteButton);
			} else {
				controller.jumpToFavorite();
			}
		});
	}

	public void setBackImage(Image image) {
		backButton.setImage(image);
	}

	public void setFavoriteImage(Image image) {
		favoriteButton.setImage(image);
	}
}
