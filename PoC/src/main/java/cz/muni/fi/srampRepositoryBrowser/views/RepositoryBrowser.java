package cz.muni.fi.srampRepositoryBrowser.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.SWT;

import cz.muni.fi.srampRepositoryBrowser.UI.ViewMain;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class RepositoryBrowser extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "cz.muni.fi.srampRepositoryBrowser.views.RepositoryBrowser";
	private ScrolledComposite ui;
	
	public void createPartControl(Composite parent) {
		ui = new ScrolledComposite(parent,SWT.H_SCROLL | SWT.V_SCROLL);
		ui.setMinHeight(350);
		ui.setMinWidth(600);
		ui.setExpandHorizontal(true);
		ui.setExpandVertical(true);
		ViewMain view = new ViewMain(ui,SWT.NONE);
		ui.setContent(view);
	}

	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		ui.setFocus();
	}
}