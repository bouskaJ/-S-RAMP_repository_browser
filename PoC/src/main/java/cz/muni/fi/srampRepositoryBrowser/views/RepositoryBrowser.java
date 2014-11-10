package cz.muni.fi.srampRepositoryBrowser.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.SWT;

import cz.muni.fi.srampRepositoryBrowser.UI.ViewMain;
import cz.muni.fi.srampRepositoryBrowser.background.BrowserManager;
import cz.muni.fi.srampRepositoryBrowser.background.BrowserManagerImpl;


/**
 * 
 * @author honza
 *	main class representing Repository Browser View
 *
 */
public class RepositoryBrowser extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "cz.muni.fi.srampRepositoryBrowser.views.RepositoryBrowser";
	private ScrolledComposite ui;
	private BrowserManager manager;
	private ViewMain mainView;
	
	
	public void createPartControl(Composite parent) {
	
		
		ui = new ScrolledComposite(parent,SWT.H_SCROLL | SWT.V_SCROLL);
		ui.setMinHeight(350);
		ui.setMinWidth(720);
		ui.setExpandHorizontal(true);
		ui.setExpandVertical(true);
		mainView= new ViewMain(ui,SWT.NONE,manager);
		ui.setContent(mainView);
		
		manager = new BrowserManagerImpl();
	}

	/**
	 * 
	 * @return browser manager
	 */
	public BrowserManager getManager()
	{
		return manager;
	}
	
	/**
	 * 
	 * @return main class with UI
	 */
	public ViewMain getMainView()
	{
		return mainView;
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		ui.setFocus();
	}

	
}