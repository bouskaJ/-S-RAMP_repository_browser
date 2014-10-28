package sramp_repository_browser.views;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.*;
import org.overlord.sramp.client.query.ArtifactSummary;
import org.overlord.sramp.client.query.QueryResultSet;

import background.BrowserManager;
import background.BrowserManagerImpl;

public class BrowserView extends ViewPart {


	public static final String ID = "carviewer.views.CarViewer";
	private Table table;
	private QueryResultSet content;
	private BrowserManager manager;

	public BrowserView() {

		manager = new BrowserManagerImpl();

	}

	public void createPartControl(Composite parent) {

		table = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		String[] titles = { "NAME", "TYPE", "CREATED BY" };

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
	
			
		}
		manager.setConnection("http://localhost:8080/s-ramp-server","admin","jejdri2nat");
		QueryResultSet content = manager.listAllArtifacts();

		for (ArtifactSummary as : content) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, as.getName());
			item.setText(1, as.getType().toString());
			item.setText(2, as.getCreatedBy());
		}

		for (int i = 0; i < 3; i++) {
			table.getColumn(i).pack();
		}
	}

	public void setFocus() {
		table.setFocus();
	}

}