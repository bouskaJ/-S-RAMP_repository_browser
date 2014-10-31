package cz.muni.fi.sramprepositorybrowser.views;



import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.SWT;

import cz.muni.fi.srampRepositoryBrowser.UI.ViewMain;




public class SrampRepositoryBrowser extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "cz.muni.fi.sramprepositorybrowser.views.SrampRepositoryBrowser";

	private ScrolledComposite ui;

	
	

	/**
	 * The constructor.
	 */
	public SrampRepositoryBrowser() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
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