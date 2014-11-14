package cz.muni.fi.srampRepositoryBrowser.UI;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.overlord.sramp.client.SrampClientQuery;
import org.overlord.sramp.client.query.ArtifactSummary;
import org.overlord.sramp.client.query.QueryResultSet;

import cz.muni.fi.srampRepositoryBrowser.background.BrowserManager;
import cz.muni.fi.srampRepositoryBrowser.background.BrowserManagerImpl;

/**
 * class representing main UI
 * 
 * @author Jan Bouska
 * 
 */
public class ViewMain extends Composite {
	private Text text;
	private org.eclipse.swt.widgets.Table table;
	private QueryResultSet content;
	private SrampClientQuery query;
	private BrowserManager browserManager;

	/**
	 * update table with data from content attribute
	 */
	public void updateTable() {

		table.removeAll();
		for (ArtifactSummary as : content) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { as.getName(), as.getType().getType(),
					as.getLastModifiedTimestamp().toString(),
					as.getCreatedTimestamp().toString() });

		}

	}
	
	

	private void createColumns() {
		String[] titles = { "NAME", "TYPE", "LAST MODIFIED", "DATE CREATED" };

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);

		}

		table.getColumn(0).setWidth(120);
		table.getColumn(1).setWidth(100);
		table.getColumn(2).setWidth(220);
		table.getColumn(3).setWidth(220);

	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 * @param manager
	 */
	public ViewMain(Composite parent, int style) {
		super(parent, style);
		browserManager = new BrowserManagerImpl();
		
		setLayout(new GridLayout(5, false));

		//query line
		Label lblQuery = new Label(this, SWT.NONE);
		lblQuery.setText("Query");

		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		//refresh button
		Button btnRefresh = new Button(this, SWT.NONE);
		btnRefresh.setText("Refresh");
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
				
				
				Job reload = new RefreshJob("refreshing data",ViewMain.this);
				reload.schedule();
				
			}
		});

		new Label(this, SWT.NONE);

		//filter
		Filter filter = new Filter(this, SWT.NONE);
		filter.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false,
				2, 1));
		filter.setSize(320, 280);

		table = new org.eclipse.swt.widgets.Table(this, SWT.BORDER
				| SWT.MULTI);

		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		createColumns();

	}

	/**
	 * 
	 * @return get browser manager
	 */
	public BrowserManager getManager() {
		return browserManager;
	}

	
	/**
	 * set query
	 * @param query 
	 */
	public void setQuery(SrampClientQuery query)
	{
		this.query = query;
	}
	
	/**
	 * load content
	 */
	public void loadContent()
	{
		content = browserManager.ExecuteQuery(query);
	}

	

}
